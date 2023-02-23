/*
 * Copyright (c) 2017 tujia.com. All Rights Reserved.
 */
package com.tujia.myssm.api.diff.render;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.tujia.myssm.api.diff.annotation.Diff;
import com.tujia.myssm.api.diff.formatter.ValueFormatter;
import com.tujia.myssm.api.diff.utils.ReflectionUtils;
import com.tujia.myssm.api.diff.visitor.TnsVisitor;
import com.tujia.myssm.api.diff.visitor.bean.ComplexDiffValue;
import com.tujia.myssm.api.diff.visitor.bean.DiffPath;
import com.tujia.myssm.api.diff.visitor.bean.DiffValue;
import com.tujia.myssm.api.diff.visitor.bean.ModifyValue;
import com.tujia.myssm.api.diff.visitor.bean.PrimitiveDiffValue;
import de.danielbechler.diff.node.DiffNode;
import de.danielbechler.diff.selector.BeanPropertyElementSelector;
import de.danielbechler.diff.selector.CollectionItemElementSelector;
import de.danielbechler.diff.selector.MapKeyElementSelector;

/**
 * @author huachen created on 2/20/14 12:03 PM
 * @version $Id$
 */
public class DiffPathBuilder {
    private static final Logger logger = LoggerFactory.getLogger(DiffPathBuilder.class);
    private final TnsVisitor tnsVisitor;
    private final Map<DiffNode, DiffPath> node2Path = Maps.newHashMap();

    public DiffPathBuilder(TnsVisitor tnsVisitor) {
        this.tnsVisitor = tnsVisitor;
    }

    private static Diff getDiffAnnotation(DiffNode diffNode) {
        return diffNode.getFieldAnnotation(Diff.class);
    }

    private static String fieldNameToString(DiffNode diffNode) {
        DiffNode parentDiffNode = diffNode.getParentNode();
        Diff fieldAnnotation = getDiffAnnotation(diffNode);
        if (fieldAnnotation != null) {
            return fieldAnnotation.value();
        } else if (diffNode.getElementSelector() instanceof BeanPropertyElementSelector) {
            Field diffField = ReflectionUtils.findField(parentDiffNode.getValueType(),
                                                        ((BeanPropertyElementSelector) diffNode.getElementSelector()).getPropertyName());
            if (diffField != null) {
                return diffField.getName();
            }
        } else if (diffNode.getElementSelector() instanceof CollectionItemElementSelector) {
            return diffNode.getElementSelector().toHumanReadableString();
        }
        return diffNode.getElementSelector().toHumanReadableString();
    }

    public DiffPath createDiffPath() {
        Set<DiffNode> changedNodes = tnsVisitor.getChangedNodes();
        if (changedNodes.isEmpty()) {
            return null;
        }
        for (DiffNode node : changedNodes) {
            DiffPath diffPath = printPath(node);
            ModifyValue modifyValue = printChanges(node);
            diffPath.setLeaf(true);
            diffPath.setModifyValue(modifyValue);
        }

        for (DiffPath diffPath : node2Path.values()) {
            if (diffPath.getParent() == null) {
                return diffPath;
            }
        }

        return null;
    }

    private DiffPath printPath(DiffNode diffNode) {
        DiffPath diffPath = node2Path.get(diffNode);
        if (diffPath == null) {
            diffPath = new DiffPath();
            node2Path.put(diffNode, diffPath);
        }

        if (diffNode.getParentNode() != null) {
            DiffNode parentDiffNode = diffNode.getParentNode();
            String propertyReadableName = toHumanReadable(diffNode);
            DiffPath parentDiffPath = printPath(parentDiffNode);
            if (parentDiffNode != null) {
                parentDiffPath.getChildren().add(diffPath);
            }
            diffPath.setParent(parentDiffPath);
            if (!propertyReadableName.isEmpty()) {
                diffPath.setName(propertyReadableName);
            }
        } else {
            Class<?> valueType = diffNode.getValueType();
            Diff annotation = valueType.getAnnotation(Diff.class);
            if (annotation != null) {
                diffPath.setName(annotation.value());
            } else {
                diffPath.setName(valueType.getName());
            }
        }
        return diffPath;
    }

    private ModifyValue printChanges(DiffNode diffNode) {
        if (diffNode.isAdded()) {
            return new ModifyValue(null, printAddOrRemoved(diffNode));
        } else if (diffNode.isRemoved()) {
            return new ModifyValue(printAddOrRemoved(diffNode), null);
        } else if (diffNode.isChanged()) {
            ImmutablePair<DiffValue, DiffValue> diffValueDiffValuePair = printModified(diffNode);
            return new ModifyValue(diffValueDiffValuePair.getLeft(), diffValueDiffValuePair.getRight());
        }
        return null;
    }

    private String toHumanReadable(DiffNode diffNode) {
        StringBuilder resultString = new StringBuilder();
        DiffNode parentDiffNode = diffNode.getParentNode();

        if (diffNode.getElementSelector() instanceof BeanPropertyElementSelector) {
            resultString.append(fieldNameToString(diffNode));
        } else if (parentDiffNode != null && diffNode.getElementSelector() instanceof MapKeyElementSelector) {
            MapKeyElementSelector mapElement = (MapKeyElementSelector) diffNode.getElementSelector();
            resultString.append(mapElement.toHumanReadableString());
        }

        return resultString.toString();
    }

    private DiffValue printAddOrRemoved(DiffNode diffNode) {
        if (diffNode.hasChildren()) {
            List<DiffValue> diffValues = new ArrayList<DiffValue>();
            diffNode.visitChildren(
                    (node, visit) -> diffValues.add(new ComplexDiffValue(fieldNameToString(node), ImmutableList.of(printAddOrRemoved(node)))));
            ComplexDiffValue complexNamedValue = new ComplexDiffValue(fieldNameToString(diffNode), diffValues);
            return complexNamedValue;
        } else {
            return new PrimitiveDiffValue(diffNode.canonicalGet(diffNode.isAdded() ? tnsVisitor.getWorking() : tnsVisitor.getBase()),
                                          getValueFormatter(diffNode));
        }
    }

    private ImmutablePair<DiffValue, DiffValue> printModified(DiffNode diffNode) {
        ValueFormatter valueFormatter = getValueFormatter(diffNode);
        DiffValue from = new PrimitiveDiffValue(diffNode.canonicalGet(tnsVisitor.getBase()), valueFormatter);
        DiffValue to = new PrimitiveDiffValue(diffNode.canonicalGet(tnsVisitor.getWorking()), valueFormatter);
        return ImmutablePair.of(from, to);
    }

    private ValueFormatter getValueFormatter(DiffNode diffNode) {
        Diff diff = getDiffAnnotation(diffNode);
        try {
            return diff == null ? null : diff.valueFormatter().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
