/*
 * Copyright (c) 2017 tujia.com. All Rights Reserved.
 */
package com.tujia.myssm.core.diff.visitor.bean;

import com.tujia.myssm.core.diff.formatter.ValueFormatter;

/**
 * @author huachen created on 2/20/14 4:50 PM
 * @version $Id$
 */
public class PrimitiveDiffValue implements DiffValue {
    private final ValueFormatter valueFormatter;
    private Object value;

    public PrimitiveDiffValue(Object value, ValueFormatter valueFormatter) {
        this.value = value;
        this.valueFormatter = valueFormatter;
    }

    //简单替换原有旧值
    public DiffValue modifyValue(DiffValue diffValue) {
        if (null == diffValue) {
            return this;
        }
        if (diffValue instanceof PrimitiveDiffValue) {
            this.value = ((PrimitiveDiffValue) diffValue).value;
        }
        return this;
    }

    @Override
    public String toString() {
        if (value == null) {
            return "";
        }
        if (valueFormatter != null) {
            return valueFormatter.format(value);
        }
        return value.toString();
    }
}
