package com.tujia.myssm.common.expression.enums;

import com.tujia.myssm.common.expression.operation.DateOperation;
import com.tujia.myssm.common.expression.operation.NumberOperation;
import com.tujia.myssm.common.expression.operation.Operation;
import com.tujia.myssm.common.expression.operation.StringOperation;
import com.tujia.myssm.common.expression.operation.TimeOperation;
import com.tujia.myssm.common.expression.operation.WeekOperation;

/**
 *
 * @author: songlinl
 * @create: 2022/12/28 11:38
 */
public enum DataType {

    string(1, "字符串", StringOperation.getInstance()),
    number(2, "数值", NumberOperation.getInstance()),
    date(3, "日期(2009-12-20 00:00:00)", DateOperation.getInstance()),
    time(4, "时间(00:00:00)", TimeOperation.getInstance()),
    week(5, "周几(1~7)", WeekOperation.getInstance()),
    ;

    private int code;

    private String desc;
    private Operation operation;

    DataType(int code, String desc, Operation operation) {
        this.code = code;
        this.desc = desc;
        this.operation = operation;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public Operation getOperation() {
        return operation;
    }

}
