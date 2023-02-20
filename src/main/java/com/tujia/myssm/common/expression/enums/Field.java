package com.tujia.myssm.common.expression.enums;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Map;
import com.google.common.collect.Maps;
import static com.tujia.myssm.common.expression.enums.FieldDisplayBit.rpDeduct;
import static com.tujia.myssm.common.expression.enums.FieldDisplayBit.rpSendTime;
import static com.tujia.myssm.common.expression.enums.FieldDisplayBit.rpUseTime;
import com.tujia.myssm.common.expression.function.AtStoreDateFunction;
import com.tujia.myssm.common.expression.function.AtStoreWeekFunction;
import com.tujia.myssm.common.expression.function.DataCalFunction;
import com.tujia.myssm.common.expression.function.DefaultDataFunction;
import com.tujia.myssm.common.expression.model.AviatorInput;

/**
 * 字段枚举
 * @author: songlinl
 * @create: 2022/12/28 11:35
 */
public enum Field {
    chInDate(1, "入住日期", DataType.date, FieldDisplayBit.bitwiseOr(rpUseTime)),
    chOutDate(2, "离店日期", DataType.date, FieldDisplayBit.bitwiseOr(rpUseTime)),
    chInWeek(3, "入住星期", DataType.week, FieldDisplayBit.bitwiseOr(rpUseTime)),
    chOutWeek(4, "离店星期", DataType.week, FieldDisplayBit.bitwiseOr(rpUseTime)),
    bookingDate(5, "预定日期", DataType.date, FieldDisplayBit.bitwiseOr(rpUseTime)),
    bookingTime(6, "预定时间", DataType.time, FieldDisplayBit.bitwiseOr(rpUseTime)),
    bookingWeek(7, "预定星期", DataType.week, FieldDisplayBit.bitwiseOr(rpUseTime)),

    // 在店指入离日期中的每一天，需特殊计算
    atStoreDate(8, "在店日期", DataType.date, FieldDisplayBit.bitwiseOr(rpUseTime)),
    atStoreWeek(9, "在店星期", DataType.week, FieldDisplayBit.bitwiseOr(rpUseTime)),

    requestDate(10, "请求日期", DataType.date, FieldDisplayBit.bitwiseOr(rpSendTime)),
    requestTime(11, "请求时间", DataType.time, FieldDisplayBit.bitwiseOr(rpSendTime)),
    requestWeek(12, "请求星期", DataType.week, FieldDisplayBit.bitwiseOr(rpSendTime)),

    preOrderDay(13, "提前预定天数", DataType.number, FieldDisplayBit.bitwiseOr(rpDeduct)),
    roomNights(14, "连住天数", DataType.number, FieldDisplayBit.bitwiseOr(rpDeduct)),
    ;

    private static final Map<Integer, Field> CODE_MAP = Maps.newHashMap();
    private static final Map<Field, DataCalFunction> SPECIAL_CAL_FUNCTION = Maps.newHashMap();

    static {
        SPECIAL_CAL_FUNCTION.put(atStoreDate, AtStoreDateFunction.getInstance());
        SPECIAL_CAL_FUNCTION.put(atStoreWeek, AtStoreWeekFunction.getInstance());

        for (Field field : Field.values()) {
            CODE_MAP.put(field.getCode(), field);
        }
    }

    private int code;
    private String desc;
    private DataType dataType;
    /**
     * 字段展示位：前端那个地方展示
     */
    private long displayBit;

    Field(int code, String desc, DataType dataType, long displayBit) {
        this.code = code;
        this.desc = desc;
        this.dataType = dataType;
        this.displayBit = displayBit;
    }

    public static Field codeOf(Integer code) {
        if (code == null) {
            return null;
        }
        return CODE_MAP.getOrDefault(code, null);
    }

    public static AviatorInput getDefaultInput() {
        AviatorInput input = new AviatorInput();
        input.setChInDate(new Date());
        input.setChOutDate(new Date());
        input.setChInWeek(LocalDate.now().getDayOfWeek().getValue());
        input.setChOutWeek(LocalDate.now().getDayOfWeek().getValue());

        input.setBookingDate(new Date());
        input.setBookingTime(LocalTime.now());
        input.setBookingWeek(LocalDate.now().getDayOfWeek().getValue());

        input.setRequestDate(new Date());
        input.setRequestTime(LocalTime.now());
        input.setRequestWeek(LocalDate.now().getDayOfWeek().getValue());

        input.setRoomNights(1L);
        input.setPreOrderDay(1L);

        return input;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public DataType getDataType() {
        return dataType;
    }

    public DataCalFunction getDataCalFunction() {
        return SPECIAL_CAL_FUNCTION.getOrDefault(this, DefaultDataFunction.getInstance());
    }

    public long getDisplayBit() {
        return displayBit;
    }
}


