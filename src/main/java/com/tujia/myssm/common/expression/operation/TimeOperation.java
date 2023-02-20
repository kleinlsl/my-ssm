package com.tujia.myssm.common.expression.operation;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author: songlinl
 * @create: 2023/01/16 10:04
 */
public class TimeOperation implements Operation<LocalTime> {
    private static final TimeOperation INSTANCE = new TimeOperation();
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("HH:mm:ss");

    private TimeOperation() {
    }

    public static TimeOperation getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean validate(String input) {
        try {
            LocalTime.parse(input, DTF);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String toAviator(String input) {
        LocalTime time = LocalTime.parse(input, DTF);
        return String.valueOf(time.toSecondOfDay());
    }

    @Override
    public LocalTime toObject(String input) {
        return LocalTime.parse(input, DTF);
    }

    @Override
    public int compare(LocalTime base, LocalTime input) {
        if (base.equals(input)) {
            return 0;
        }
        return base.compareTo(input);
    }

    @Override
    public int compare(LocalTime base, String input) {
        return compare(base, toObject(input));
    }
}