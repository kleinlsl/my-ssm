package com.tujia.myssm.common.expression.model;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tujia.myssm.common.expression.enums.Field;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author: songlinl
 * @create: 2023/01/16 10:19
 */
@Getter
@Setter
public class AviatorInput implements Serializable {
    public static final String AVIATOR_INPUT = "AVIATOR_INPUT";
    private static final long serialVersionUID = 185622235653840706L;
    private Date chInDate;
    private Date chOutDate;
    private Integer chInWeek;
    private Integer chOutWeek;

    private Date bookingDate;
    private LocalTime bookingTime;
    private Integer bookingWeek;

    private Date requestDate;
    private LocalTime requestTime;
    private Integer requestWeek;

    private Long preOrderDay;
    private Long roomNights;

    @JsonIgnore
    public Object getByField(Field field) {
        if (field == null) {
            return null;
        }
        switch (field) {
            case chInDate:
                return chInDate;
            case chOutDate:
                return chOutDate;
            case chInWeek:
                return chInWeek;
            case chOutWeek:
                return chOutWeek;
            case bookingDate:
                return bookingDate;
            case bookingTime:
                return bookingTime;
            case bookingWeek:
                return bookingWeek;

            case requestDate:
                return requestDate;
            case requestTime:
                return requestTime;
            case requestWeek:
                return requestWeek;

            case preOrderDay:
                return preOrderDay;
            case roomNights:
                return roomNights;
            default:
                throw new NullPointerException("未知的field");
        }
    }

    public Map<String, Object> toAviatorEnvMap() {
        Map<String, Object> map = new HashMap<>();
        map.put(Field.chInDate.name(), chInDate);
        map.put(Field.chOutDate.name(), chOutDate);
        map.put(Field.chInWeek.name(), chInWeek);
        map.put(Field.chOutWeek.name(), chOutWeek);

        if (bookingTime != null) {
            map.put(Field.bookingTime.name(), bookingTime.toSecondOfDay());
        }
        map.put(Field.bookingDate.name(), bookingDate);
        map.put(Field.bookingWeek.name(), bookingWeek);

        if (requestTime != null) {
            map.put(Field.requestTime.name(), requestTime.toSecondOfDay());
        }
        map.put(Field.requestDate.name(), requestDate);
        map.put(Field.requestWeek.name(), requestWeek);

        map.put(Field.preOrderDay.name(), preOrderDay);
        map.put(Field.roomNights.name(), preOrderDay);
        map.put(AVIATOR_INPUT, this);
        return map;
    }

}
