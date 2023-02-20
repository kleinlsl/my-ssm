package com.tujia.myssm.common.expression.operation;

/**
 *
 * @author: songlinl
 * @create: 2023/01/16 10:03
 */
public class NumberOperation implements Operation<Long> {
    private static final NumberOperation INSTANCE = new NumberOperation();

    private NumberOperation() {
    }

    public static NumberOperation getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean validate(String input) {
        try {
            Long.parseLong(input.trim());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String toAviator(String input) {
        return input.trim();
    }

    @Override
    public Long toObject(String input) {
        return Long.parseLong(input);
    }

    @Override
    public int compare(Long base, Long input) {
        if (base.equals(input)) {
            return 0;
        }
        return base.compareTo(input);
    }

    @Override
    public int compare(Long base, String input) {
        Long inputVal = toObject(input);
        return compare(base, inputVal);
    }
}