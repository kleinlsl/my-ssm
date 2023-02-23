/*
 * Copyright (c) 2017 tujia.com. All Rights Reserved.
 */
package com.tujia.myssm.api.diff.visitor.bean;

import java.util.ArrayList;
import java.util.List;
import de.danielbechler.util.Strings;

/**
 * @author huachen created on 2/20/14 4:49 PM
 * @version $Id$
 */
public class ComplexDiffValue implements DiffValue {
    private static final String START = "[";
    private static final String END = "]";
    private static final String MAPPING_SYMBOL = "=";
    private static final String SEPERATOR = ";";

    private final String name;
    private final List<DiffValue> diffValues;

    public ComplexDiffValue(String name, List<DiffValue> diffValues) {
        this.name = name;
        this.diffValues = diffValues;
    }

    public ComplexDiffValue() {
        this(null, new ArrayList<DiffValue>());
    }

    public void add(DiffValue diffValue) {
        this.diffValues.add(diffValue);
    }

    public List<DiffValue> getDiffValues() {
        return diffValues;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.diffValues.size() > 1) {
            sb.append(START);
        }
        if (name != null) {
            sb.append(name).append(MAPPING_SYMBOL);
        }
        sb.append(Strings.join(SEPERATOR, diffValues));
        if (this.diffValues.size() > 1) {
            sb.append(END);
        }
        return sb.toString();
    }
}
