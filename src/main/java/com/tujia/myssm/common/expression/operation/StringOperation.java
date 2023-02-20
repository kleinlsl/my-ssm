package com.tujia.myssm.common.expression.operation;

/**
 *
 * @author: songlinl
 * @create: 2023/01/16 10:03
 */
public class StringOperation implements Operation<String> {
    private static final StringOperation INSTANCE = new StringOperation();

    private StringOperation() {
    }

    public static StringOperation getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean validate(String input) {
        try {
            return input != null;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String toAviator(String input) {
        return input;
    }

    @Override
    public String toObject(String input) {
        return input;
    }

    @Override
    public int compare(String base, String input) {
        if (base.equals(input)) {
            return 0;
        }
        return base.compareTo(input);
    }
}
