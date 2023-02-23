package com.tujia.myssm.api.diff.visitor;

import java.util.Set;
import com.google.common.collect.Sets;
import com.tujia.myssm.api.diff.annotation.Atomic;
import com.tujia.myssm.api.diff.annotation.DiffIgnore;
import de.danielbechler.diff.node.DiffNode;
import de.danielbechler.diff.node.Visit;

/**
 *
 * @author: songlinl
 * @create: 2023/02/22 19:51
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
        if ((node.isRootNode() && !node.hasChanges()) || (node.hasChanges() && !node.hasChildren())) {
            return true;
        }
        Atomic atomic = node.getFieldAnnotation(Atomic.class);
        if (atomic != null) {
            return true;
        }
        return false;
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
        return null != diffNode.getFieldAnnotation(DiffIgnore.class);
    }

    public Set<DiffNode> getChangedNodes() {
        return changedNodes;
    }
}
