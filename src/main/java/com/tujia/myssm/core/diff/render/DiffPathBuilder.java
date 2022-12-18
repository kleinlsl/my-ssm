/*
 * Copyright (c) 2017 tujia.com. All Rights Reserved.
 */
package com.tujia.myssm.core.diff.render;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.tujia.myssm.core.diff.annotation.Diff;
import com.tujia.myssm.core.diff.annotation.DiffEntity;
import com.tujia.myssm.core.diff.danielbechler.diff.node.DiffNode;
import com.tujia.myssm.core.diff.danielbechler.diff.selector.BeanPropertyElementSelector;
import com.tujia.myssm.core.diff.danielbechler.diff.selector.CollectionItemElementSelector;
import com.tujia.myssm.core.diff.danielbechler.diff.selector.MapKeyElementSelector;
import com.tujia.myssm.core.diff.formatter.ValueFormatter;
import com.tujia.myssm.core.diff.utils.ReflectionUtils;
import com.tujia.myssm.core.diff.visitor.TnsVisitor;
import com.tujia.myssm.core.diff.visitor.bean.ComplexDiffValue;
import com.tujia.myssm.core.diff.visitor.bean.DiffPath;
import com.tujia.myssm.core.diff.visitor.bean.DiffValue;
import com.tujia.myssm.core.diff.visitor.bean.ModifyValue;
import com.tujia.myssm.core.diff.visitor.bean.PrimitiveDiffValue;

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
        DiffNode parentDiffNode = diffNode.getParentNode();
        if (diffNode.getElementSelector() instanceof BeanPropertyElementSelector) {
            Field diffField = ReflectionUtils.findField(parentDiffNode.getValueType(),
                                                        ((BeanPropertyElementSelector) diffNode.getElementSelector()).getPropertyName());
            if (diffField == null) {
                return null;
            }
            return diffField.getAnnotation(Diff.class);
        }
        return null;
    }

    private static String fieldNameToString(DiffNode diffNode) {
        DiffNode parentDiffNode = diffNode.getParentNode();
        if (diffNode.getElementSelector() instanceof BeanPropertyElementSelector) {
            Diff fieldAnnotation = getDiffAnnotation(diffNode);
            if (fieldAnnotation != null) {
                return fieldAnnotation.value();
            } else {
                Field diffField = ReflectionUtils.findField(parentDiffNode.getValueType(),
                                                            ((BeanPropertyElementSelector) diffNode.getElementSelector()).getPropertyName());
                if (diffField == null) {
                    return "";
                }
                return diffField.getName();
            }
        }
        return diffNode.getElementSelector().toString();
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
            node2Path.put(diffNode, diffPath = new DiffPath());
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
        diffPath.setAbsolutePath(diffNode.getAbsolutePath());
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
            resultString.append(String.format("[%s]", mapElement.getKey()));
        } else if (diffNode.getElementSelector() instanceof CollectionItemElementSelector) {
            CollectionItemElementSelector elementSelector = (CollectionItemElementSelector) diffNode.getElementSelector();
            Object item = elementSelector.getItem();
            if (item == null) {
                logger.debug("diffNode:{},item is null", diffNode);
            } else {
                Class<?> tempClass = item.getClass();
                Field identityFiled = null;
                while (tempClass != null && !tempClass.getName().toLowerCase().equals("java.lang.object")) {
                    for (Field field : tempClass.getDeclaredFields()) {
                        if (field.getAnnotation(DiffEntity.class) != null) {
                            identityFiled = field;
                            break;
                        }
                    }
                    if (identityFiled != null) {
                        identityFiled.setAccessible(true);
                        try {
                            if (!tnsVisitor.getChangedNodes().contains(diffNode)) {
                                if (item.getClass().getAnnotation(Diff.class) != null) {
                                    resultString.append(item.getClass().getAnnotation(Diff.class).value());
                                } else {
                                    resultString.append(item.getClass().getSimpleName());
                                }
                                resultString.append(":").append(identityFiled.get(item));
                            }
                        } catch (IllegalAccessException e) {
                            logger.warn("diff工具错误:{}", diffNode, e);
                        }
                        break;  // end the while loop
                    }
                    tempClass = tempClass.getSuperclass();
                }
            }
        }

        logger.debug("toHumanReadable:{}", resultString.toString());
        return resultString.toString();
    }

    private DiffValue printAddOrRemoved(DiffNode diffNode) {
        Collection<DiffNode> children = diffNode.getChildren();
        if (children != null && !children.isEmpty()) {
            ComplexDiffValue complexNamedValue = new ComplexDiffValue();
            for (DiffNode node : children) {
                //                ValueFormatter valueFormatter = getValueFormatter(node);
                //                if (valueFormatter != null) {
                //                    complexNamedValue.add(new PrimitiveDiffValue(node.canonicalGet(node.isAdded() ? tnsVisitor.getWorking() : tnsVisitor.getBase()),
                //                                                                 valueFormatter));
                //                    continue;
                //                }
                complexNamedValue.add(
                        new ComplexDiffValue(fieldNameToString(node), node.getAbsolutePath(), ImmutableList.of(printAddOrRemoved(node))));
            }
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
