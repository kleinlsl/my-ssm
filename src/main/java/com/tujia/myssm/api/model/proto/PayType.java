package com.tujia.myssm.api.model.proto;

/**
 *
 * @author: songlinl
 * @create: 2022/08/17 14:21
 */
public enum PayType {
    NONE(0, ""),
    PROXY(1, "全额预付"),
    CASH(2, "现付");
    private int code;
    private String name;

    PayType(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static PayType codeOf(int code) {
        PayType[] arr$ = values();
        int len$ = arr$.length;

        for (int i$ = 0; i$ < len$; ++i$) {
            PayType payType = arr$[i$];
            if (payType.code == code) {
                return payType;
            }
        }
        return null;
    }

    public final int getCode() {
        return this.code;
    }

    public final String getName() {
        return this.name;
    }

}
