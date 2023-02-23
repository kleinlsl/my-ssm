/*
 * Copyright (c) 2017 tujia.com. All Rights Reserved.
 */
package com.tujia.myssm.api.diff.visitor.bean;

import com.tujia.myssm.api.diff.formatter.ValueFormatter;

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
