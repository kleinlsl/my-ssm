package com.tujia.myssm.common.expression.operation;

import org.apache.commons.lang3.math.NumberUtils;

/**
 *
 * @author: songlinl
 * @create: 2023/01/16 10:04
 */
public class WeekOperation implements Operation<Integer> {
    private static final WeekOperation INSTANCE = new WeekOperation();

    private WeekOperation() {
    }

    public static WeekOperation getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean validate(String input) {
        try {
            int week = NumberUtils.toInt(input, -1);
            return week >= 1 && week <= 7;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String toAviator(String input) {
        return input;
    }

    @Override
    public Integer toObject(String input) {
        int week = NumberUtils.toInt(input, -1);
        if (week >= 1 && week <= 7) {
            return week;
        }
        throw new NumberFormatException("Week不合法");
    }

    @Override
    public int compare(Integer base, Integer input) {
        if (base.equals(input)) {
            return 0;
        }
        return base.compareTo(input);
    }

    @Override
    public int compare(Integer base, String input) {
        return compare(base, toObject(input));
    }
}