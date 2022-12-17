package com.tujia.myssm.common.date.format;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.util.Date;
import com.tujia.framework.datetime.bean.ShortDate;
import static com.tujia.myssm.common.date.constant.DateFormatConstant.ZONE_ID_G8;

/**
 *
 * @author songlinl
 * @create 2021-04-15 15:48
 */
public final class DateFormatUtils {

    private DateFormatUtils() {
    }

    /**
     * o2 在 o1 之前 返回 负数
     * @param o1
     * @param o2
     * @return
     */
    public static int days(ShortDate o1, ShortDate o2) {
        return days(toLocalDate(o1), toLocalDate(o2));
    }

    /**
     *  o2 在 o1 之前 返回 负数
     * @param o1
     * @param o2
     * @return
     */
    public static int days(LocalDate o1, LocalDate o2) {
        return Period.between(o1, o2).getDays();
    }

    /**
     * o2 在 o1 之前 返回 负数
     * @param o1
     * @param o2
     * @return
     */
    public static int days(LocalDateTime o1, LocalDateTime o2) {
        return days(o1.toLocalDate(), o2.toLocalDate());
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZONE_ID_G8);
    }

    public static long getTimestampMilliSecond(LocalDateTime localDateTime) {
        return localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    public static LocalDate toLocalDate(Date date) {
        return toLocalDateTime(date).toLocalDate();
    }

    public static LocalDate toLocalDate(ShortDate date) {
        return toLocalDateTime(date.toDate()).toLocalDate();
    }

    public static Date toDate(LocalDateTime date) {
        return Date.from(date.atZone(ZONE_ID_G8).toInstant());
    }

    public static Date toDate(LocalDate date) {
        return Date.from(date.atTime(0, 0).atZone(ZONE_ID_G8).toInstant());
    }

    public static LocalDateTime now() {
        return LocalDateTime.now(ZONE_ID_G8);
    }

    public static LocalDate today() {
        return LocalDateTime.now(ZONE_ID_G8).toLocalDate();
    }
}