/*
 * Copyright 2014 Daniel Bechler
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tujia.myssm.core.diff.danielbechler.diff.differ;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.tujia.myssm.core.diff.danielbechler.diff.access.Accessor;
import com.tujia.myssm.core.diff.danielbechler.diff.access.Instances;
import com.tujia.myssm.core.diff.danielbechler.diff.access.PropertyAwareAccessor;
import com.tujia.myssm.core.diff.danielbechler.diff.category.CategoryResolver;
import com.tujia.myssm.core.diff.danielbechler.diff.circular.CircularReferenceDetector;
import static com.tujia.myssm.core.diff.danielbechler.diff.circular.CircularReferenceDetector.CircularReferenceException;
import com.tujia.myssm.core.diff.danielbechler.diff.circular.CircularReferenceDetectorFactory;
import com.tujia.myssm.core.diff.danielbechler.diff.circular.CircularReferenceExceptionHandler;
import com.tujia.myssm.core.diff.danielbechler.diff.filtering.IsReturnableResolver;
import com.tujia.myssm.core.diff.danielbechler.diff.inclusion.IsIgnoredResolver;
import com.tujia.myssm.core.diff.danielbechler.diff.introspection.PropertyAccessExceptionHandler;
import com.tujia.myssm.core.diff.danielbechler.diff.introspection.PropertyAccessExceptionHandlerResolver;
import com.tujia.myssm.core.diff.danielbechler.diff.introspection.PropertyReadException;
import com.tujia.myssm.core.diff.danielbechler.diff.node.DiffNode;
import com.tujia.myssm.core.diff.danielbechler.diff.path.NodePath;
import com.tujia.myssm.core.diff.danielbechler.diff.selector.ElementSelector;
import com.tujia.myssm.core.diff.danielbechler.util.Assert;

/**
 * @author Daniel Bechler
 */
public class DifferDispatcher {
	private static final Logger logger = LoggerFactory.getLogger(DifferDispatcher.class);
	private static final ThreadLocal<CircularReferenceDetector> workingThreadLocal = new ThreadLocal<CircularReferenceDetector>();
	private static final ThreadLocal<CircularReferenceDetector> baseThreadLocal = new ThreadLocal<CircularReferenceDetector>();
	private final static List<String> collections = Lists.newArrayList("com.tujia.framework.common.IntSet", "java.util.List", "java.util.ArrayList",
																	   "java.util.LinkedList", "java.util.Set", "java.util.HashSet",
																	   "java.util.LinkedHashSet");
	private final DifferProvider differProvider;
	private final CircularReferenceDetectorFactory circularReferenceDetectorFactory;
	private final CircularReferenceExceptionHandler circularReferenceExceptionHandler;
	private final IsIgnoredResolver isIgnoredResolver;
	private final CategoryResolver categoryResolver;
	private final IsReturnableResolver isReturnableResolver;
	private final PropertyAccessExceptionHandlerResolver propertyAccessExceptionHandlerResolver;

	public DifferDispatcher(final DifferProvider differProvider, final CircularReferenceDetectorFactory circularReferenceDetectorFactory,
							final CircularReferenceExceptionHandler circularReferenceExceptionHandler, final IsIgnoredResolver ignoredResolver,
							final IsReturnableResolver returnableResolver,
							final PropertyAccessExceptionHandlerResolver propertyAccessExceptionHandlerResolver,
							final CategoryResolver categoryResolver) {
		Assert.notNull(differProvider, "differFactory");
		this.differProvider = differProvider;

		Assert.notNull(ignoredResolver, "ignoredResolver");
		this.isIgnoredResolver = ignoredResolver;

		Assert.notNull(categoryResolver, "categoryResolver");
		this.categoryResolver = categoryResolver;

		this.circularReferenceDetectorFactory = circularReferenceDetectorFactory;
		this.circularReferenceExceptionHandler = circularReferenceExceptionHandler;
		this.isReturnableResolver = returnableResolver;
		this.propertyAccessExceptionHandlerResolver = propertyAccessExceptionHandlerResolver;

		resetInstanceMemory();
	}

	protected static void forgetInstances(final DiffNode parentNode, final Instances instances) {
		final NodePath nodePath = getNodePath(parentNode, instances);
		logger.debug("[ {} ] Forgetting --- WORKING: {} <=> BASE: {}", nodePath, instances.getWorking(), instances.getBase());
		workingThreadLocal.get().remove(instances.getWorking());
		baseThreadLocal.get().remove(instances.getBase());
	}

	private static NodePath getNodePath(final DiffNode parentNode, final Instances instances) {
		if (parentNode == null) {
			return NodePath.withRoot();
		} else {
			final NodePath parentPath = parentNode.getPath();
			final ElementSelector elementSelector = instances.getSourceAccessor().getElementSelector();
			return NodePath.startBuildingFrom(parentPath).element(elementSelector).build();
		}
	}

	protected static void rememberInstances(final DiffNode parentNode, final Instances instances) {
		final NodePath nodePath = getNodePath(parentNode, instances);
		logger.debug("[ {} ] Remembering --- WORKING: {} <=> BASE: {}", nodePath, instances.getWorking(), instances.getBase());
		transactionalPushToCircularReferenceDetectors(nodePath, instances);
	}

	private static void transactionalPushToCircularReferenceDetectors(final NodePath nodePath, final Instances instances) {
		workingThreadLocal.get().push(instances.getWorking(), nodePath);

		// TODO This needs to be solved more elegantly. If the push for one of these detectors fails,
		// we need to make sure to revert the push to the other one, if it already happened.
		try {
			baseThreadLocal.get().push(instances.getBase(), nodePath);
		} catch (final CircularReferenceException e) {
			workingThreadLocal.get().remove(instances.getWorking()); // rollback
			throw e;
		}
	}

	private static DiffNode findNodeMatchingPropertyPath(final DiffNode node, final NodePath nodePath) {
		if (node == null) {
			return null;
		}
		if (node.matches(nodePath)) {
			return node;
		}
		return findNodeMatchingPropertyPath(node.getParentNode(), nodePath);
	}

	private static DiffNode newCircularNode(final DiffNode parentNode, final Instances instances, final NodePath circleStartPath) {
		final DiffNode node = new DiffNode(parentNode, instances.getSourceAccessor(), instances.getType());
		node.setState(DiffNode.State.CIRCULAR);
		node.setCircleStartPath(circleStartPath);
		node.setCircleStartNode(findNodeMatchingPropertyPath(parentNode, circleStartPath));
		return node;
	}

	public final void resetInstanceMemory() {
		workingThreadLocal.set(circularReferenceDetectorFactory.createCircularReferenceDetector());
		baseThreadLocal.set(circularReferenceDetectorFactory.createCircularReferenceDetector());
	}

	public final void clearInstanceMemory() {
		workingThreadLocal.remove();
		baseThreadLocal.remove();
	}

	/**
	 * Delegates the call to an appropriate {@link Differ}.
	 *
	 * @return A node representing the difference between the given {@link Instances}.
	 */
	public DiffNode dispatch(final DiffNode parentNode, final Instances parentInstances, final Accessor accessor) {
		Assert.notNull(parentInstances, "parentInstances");
		Assert.notNull(accessor, "accessor");

		final DiffNode node = compare(parentNode, parentInstances, accessor);
		if (parentNode != null && isReturnableResolver.isReturnable(node)) {
			parentNode.addChild(node);
		}
		if (node != null) {
			node.addCategories(categoryResolver.resolveCategories(node));
		}
		return node;
	}

	private DiffNode compare(final DiffNode parentNode, final Instances parentInstances, final Accessor accessor) {
		final DiffNode node = new DiffNode(parentNode, accessor, null);
		if (isIgnoredResolver.isIgnored(node)) {
			node.setState(DiffNode.State.IGNORED);
			return node;
		}

		final Instances accessedInstances;
		if (accessor instanceof PropertyAwareAccessor) {
			final PropertyAwareAccessor propertyAwareAccessor = (PropertyAwareAccessor) accessor;
			try {
				accessedInstances = parentInstances.access(accessor);
			} catch (final PropertyReadException e) {
				node.setState(DiffNode.State.INACCESSIBLE);
				final Class<?> parentType = parentInstances.getType();
				final String propertyName = propertyAwareAccessor.getPropertyName();
				final PropertyAccessExceptionHandler exceptionHandler = propertyAccessExceptionHandlerResolver.resolvePropertyAccessExceptionHandler(
						parentType, propertyName);
				if (exceptionHandler != null) {
					exceptionHandler.onPropertyReadException(e, node);
				}
				return node;
			}
		} else {
			accessedInstances = parentInstances.access(accessor);
		}

		if (accessedInstances.areNull()) {
			return new DiffNode(parentNode, accessedInstances.getSourceAccessor(), accessedInstances.getType());
		} else {
			return compareWithCircularReferenceTracking(parentNode, accessedInstances);
		}
	}

	private DiffNode compareWithCircularReferenceTracking(final DiffNode parentNode, final Instances instances) {
		DiffNode node = null;
		try {
			rememberInstances(parentNode, instances);
			try {
				node = compare(parentNode, instances);
			} finally {
				if (node != null) {
					forgetInstances(parentNode, instances);
				}
			}
			try {
				if (node != null && parentNode != null && parentNode.getValueType() != null) {
					if (checkCollection(parentNode.getValueType().getName())) {
						node.setAbsolutePath(parentNode.getParentNode().getValueType().getName() + "." + parentNode.getPropertyName());
					} else {
						node.setAbsolutePath(parentNode.getValueType().getName() + "." +
													 instances.getSourceAccessor().getElementSelector().toHumanReadableString());
					}
				}
			} catch (Exception e) {
				logger.info("获取node绝对路径异常，error msg = {}", e.getMessage(), e);
			}
		} catch (final CircularReferenceException e) {
			node = newCircularNode(parentNode, instances, e.getNodePath());
			circularReferenceExceptionHandler.onCircularReferenceException(node);
		}
		if (parentNode == null) {
			resetInstanceMemory();
		}
		return node;
	}

	/**
	 * 检查是否是集合
	 *
	 * @param clazz 绝对路径
	 * @return boolean true 是，false 不是
	 */
	private boolean checkCollection(String clazz) {
		if (Strings.isNullOrEmpty(clazz)) {
			return false;
		}
		for (String item : collections) {
			if (Strings.isNullOrEmpty(item)) {
				continue;
			}
			if (Objects.equal(item, clazz)) {
				return true;
			}
		}
		return false;
	}

	private DiffNode compare(final DiffNode parentNode, final Instances instances) {
		final Differ differ = differProvider.retrieveDifferForType(instances.getType());
		if (differ == null) {
			throw new IllegalStateException("Couldn't create Differ for type '" + instances.getType() +
													"'. This mustn't happen, as there should always be a fallback differ.");
		}
		return differ.compare(parentNode, instances);
	}
}
