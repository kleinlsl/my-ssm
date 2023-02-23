/*
 * Copyright (c) 2017 tujia.com. All Rights Reserved.
 */
package com.tujia.myssm.api.diff.visitor.bean;

/**
 * @author huachen created on 2/21/14 2:42 PM
 * @version $Id$
 */
public class ModifyValue {
    private final DiffValue base;
    private final DiffValue working;

    public ModifyValue(DiffValue base, DiffValue working) {
        this.base = base;
        this.working = working;
    }

    public DiffValue getBase() {
        return base;
    }

    public DiffValue getWorking() {
        return working;
    }

    public boolean isAdded() {
        return base == null && working != null;
    }

    public boolean isRemoved() {
        return base != null && working == null;
    }

    public boolean isChanged() {
        return base != null && working != null && !base.equals(working);
    }
}
