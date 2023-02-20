package com.tujia.myssm.common.expression.enums;

import java.util.Arrays;
import java.util.List;
import com.google.common.collect.Lists;

/**
 * 操作符
 * @author: songlinl
 * @create: 2022/12/28 13:39
 */
public enum Operator {
    eq(100, "==", "等于"),
    neq(101, "!=", "不等于"),
    lt(102, "<", "小于"),
    gt(103, ">", "大于"),
    le(104, "<=", "小于等于"),
    ge(105, ">=", "大于等于"),

    and(200, "&&", "且"),
    or(201, "||", "或"),

    in(601, "in", "包含"),
    not_in(602, "notIn", "不包含"),
    ;

    private static final List<Operator> logicOperators = Lists.newArrayList();
    private static final List<Operator> ALL = Arrays.asList(Operator.values());

    static {
        logicOperators.add(Operator.and);
        logicOperators.add(Operator.or);
    }

    private int code;
    private String charCode;
    private String desc;

    Operator(int code, String charCode, String desc) {
        this.code = code;
        this.charCode = charCode;
        this.desc = desc;
    }

    public static Operator codeOf(Integer code) {
        if (code == null) {
            return null;
        }
        return ALL.stream().filter(type -> type.getCode() == code).findFirst().orElse(null);
    }

    public static List<Operator> getAllOperator() {
        return ALL;
    }

    public int getCode() {
        return code;
    }

    public String getCharCode() {
        return charCode;
    }

    public String getDesc() {
        return desc;
    }

    /**
     * 是否逻辑操作符
     */
    public boolean isLogic() {
        return logicOperators.contains(this);
    }
}
