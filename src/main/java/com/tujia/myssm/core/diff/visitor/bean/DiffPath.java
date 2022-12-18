/*
 * Copyright (c) 2017 tujia.com. All Rights Reserved.
 */
package com.tujia.myssm.core.diff.visitor.bean;

import java.util.Set;
import com.google.common.collect.Sets;

/**
 * @author huachen created on 2/21/14 2:07 PM
 * @version $Id$
 */
public class DiffPath {
    private boolean leaf;
    private String name;
    private DiffPath parent;
    private Set<DiffPath> children = Sets.newLinkedHashSet();
    private ModifyValue modifyValue;

    // node的绝对路径
    private String absolutePath = "";

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public DiffPath getParent() {
        return parent;
    }

    public void setParent(DiffPath parent) {
        this.parent = parent;
    }

    public Set<DiffPath> getChildren() {
        return children;
    }

    public void setChildren(Set<DiffPath> children) {
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ModifyValue getModifyValue() {
        return modifyValue;
    }

    public void setModifyValue(ModifyValue modifyValue) {
        this.modifyValue = modifyValue;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }
}
