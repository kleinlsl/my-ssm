package com.tujia.myssm.core.diff.visitor;

/*
 * Copyright (c) 2017 tujia.com. All Rights Reserved.
 */

import java.lang.reflect.Field;
import java.util.Set;
import com.google.common.collect.Sets;
import com.tujia.myssm.core.diff.annotation.DiffIgnore;
import com.tujia.myssm.core.diff.danielbechler.diff.node.DiffNode;
import com.tujia.myssm.core.diff.danielbechler.diff.node.Visit;
import com.tujia.myssm.core.diff.danielbechler.diff.selector.BeanPropertyElementSelector;
import com.tujia.myssm.core.diff.utils.ReflectionUtils;

/**
 * @author huachen created on 2/15/14 11:21 AM
 * @version $Id$
 */
public class TnsVisitor implements DiffNode.Visitor {
    private final Set<DiffNode> changedNodes;
    private final Object working;
    private final Object base;

    public TnsVisitor(Object working, Object base) {
        this.working = working;
        this.base = base;
        changedNodes = Sets.newLinkedHashSet();
    }

    private static boolean filter(final DiffNode node) {
        return (node.isRootNode() && !node.hasChanges()) || (node.hasChanges() && node.getChildren().isEmpty());
    }

    private static DiffNode findFirstNode(DiffNode diffNode) {
        DiffNode parentNode = diffNode.getParentNode();
        if (parentNode != null && (parentNode.isAdded() || parentNode.isRemoved())) {
            return findFirstNode(parentNode);
        } else {
            return diffNode;
        }
    }

    @Override
    public void node(DiffNode node, Visit visit) {
        if (filter(node)) {
            path2Content(node);
        }
    }

    public Object getWorking() {
        return working;
    }

    public Object getBase() {
        return base;
    }

    private void path2Content(DiffNode diffNode) {

        // 若该属性带有Ignore注解，那么直接忽略这个属性
        try {
            if (isIgnored(diffNode)) {
                return;
            }
        } catch (NoSuchFieldException e) {

            e.printStackTrace();
        }

        if (diffNode.isAdded() || diffNode.isRemoved()) {
            changedNodes.add(findFirstNode(diffNode));
        } else if (diffNode.isChanged()) {
            changedNodes.add(diffNode);
        }
    }

    private boolean isIgnored(DiffNode diffNode) throws NoSuchFieldException {
        DiffNode parentDiffNode = diffNode.getParentNode();
        if (diffNode.getElementSelector() instanceof BeanPropertyElementSelector) {
            Field diffField = ReflectionUtils.findField(parentDiffNode.getValueType(),
                                                        ((BeanPropertyElementSelector) diffNode.getElementSelector()).getPropertyName());
            if (diffField == null) {
                return false;
            }
            if (null != diffField.getAnnotation(DiffIgnore.class)) {
                return true;
            }
        }
        return false;
    }

    public Set<DiffNode> getChangedNodes() {
        return changedNodes;
    }
}
