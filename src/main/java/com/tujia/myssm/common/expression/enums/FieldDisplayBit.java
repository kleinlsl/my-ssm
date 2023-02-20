package com.tujia.myssm.common.expression.enums;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author: songlinl
 * @create: 2023/01/05 12:55
 */
public enum FieldDisplayBit {
    rpSendTime(0, 1L << 0, "红包发放时间条件"),
    rpUseTime(1, 1L << 1, "红包使用时间条件"),
    rpDeduct(2, 1L << 2, "红包优惠规则条件"),
    ;

    private static final List<FieldDisplayBit> ALL = Arrays.asList(FieldDisplayBit.values());
    private int code;
    private long bit;
    private String desc;

    FieldDisplayBit(int code, long bit, String desc) {
        this.code = code;
        this.bit = bit;
        this.desc = desc;
    }

    public static FieldDisplayBit codeOf(Integer code) {
        if (code == null) {
            return null;
        }
        return ALL.stream().filter(e -> e.getCode() == code).findFirst().orElse(null);
    }

    public static long bitwiseOr(FieldDisplayBit... bits) {
        long res = 0;
        for (FieldDisplayBit bit : bits) {
            res = res | bit.getBit();
        }
        return res;
    }

    public static boolean contains(long flag, FieldDisplayBit bit) {
        return (flag & bit.getBit()) == bit.getBit();
    }

    public int getCode() {
        return code;
    }

    public long getBit() {
        return bit;
    }

    public String getDesc() {
        return desc;
    }

}
