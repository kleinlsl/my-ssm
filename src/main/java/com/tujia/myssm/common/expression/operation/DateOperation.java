package com.tujia.myssm.common.expression.operation;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author: songlinl
 * @create: 2023/01/16 10:04
 */
public class DateOperation implements Operation<Date> {
    private static final DateOperation INSTANCE = new DateOperation();
    private static final String strYmdHms = "yyyy-MM-dd HH:mm:ss";

    private DateOperation() {
    }

    public static DateOperation getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean validate(String input) {
        try {
            new SimpleDateFormat(strYmdHms).parse(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String toAviator(String input) {
        return "'" + input + ":00'";
    }

    @Override
    public Date toObject(String input) {
        try {
            return new SimpleDateFormat(strYmdHms).parse(input);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int compare(Date base, Date input) {
        if (base.equals(input)) {
            return 0;
        }
        return base.compareTo(input);
    }

    @Override
    public int compare(Date base, String input) {
        Date inputVal = toObject(input);
        return compare(base, inputVal);
    }

}