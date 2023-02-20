package com.tujia.myssm.common.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by wenyueq on 2018/5/7
 */
public class DateUtils {

    public static String strYmd = "yyyy-MM-dd";
    public static String strYmdHms = "yyyy-MM-dd HH:mm:ss";
    public static java.time.format.DateTimeFormatter ymd = java.time.format.DateTimeFormatter.ofPattern(strYmd);
    public static java.time.format.DateTimeFormatter ymdhms = java.time.format.DateTimeFormatter.ofPattern(strYmdHms);

    /**
     * 时间文本转时间对象
     *
     * @param dateStr yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static Date dateStrToDate(String dateStr) {
        try {
            return new SimpleDateFormat(strYmdHms).parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 时间对象转时间文本
     *
     * @param date
     * @return
     */
    public static String dateToDateStr(Date date, String formatter) {
        return new SimpleDateFormat(formatter).format(date);
    }

    /**
     * 获取当前至季度末秒值
     *
     * @return
     */
    public static Long getQuarterEndSecond() {
        LocalDate localDateNow = LocalDate.now();
        int quarterInMonth = getQuarterInMonth(localDateNow.getMonthValue(), false);
        LocalDate localDateMonth = localDateNow.withMonth(quarterInMonth);
        LocalDate localDate = localDateMonth.with(TemporalAdjusters.lastDayOfMonth());
        LocalTime localTime = LocalTime.of(23, 59, 59, 999999999);
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        long endTime = localDateTime.toEpochSecond(ZoneOffset.of("+8"));
        long startTime = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        return endTime - startTime;
    }

    // 返回第几个月份，不是几月
    // 季度一年四季， 第一季度：2月-4月， 第二季度：5月-7月， 第三季度：8月-10月， 第四季度：11月-1月
    private static int getQuarterInMonth(int month, boolean isQuarterStart) {
        int months[] = { 1, 4, 7, 10 };
        if (!isQuarterStart) {
            months = new int[] { 3, 6, 9, 12 };
        }
        if (month >= 1 && month <= 3)
            return months[0];
        else if (month >= 4 && month <= 6)
            return months[1];
        else if (month >= 7 && month <= 9)
            return months[2];
        else
            return months[3];
    }

    /**
     * 时加减
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date hourOperation(Date date, int amount) {
        Calendar cal = Calendar.getInstance();//使用默认时区和语言环境获得一个日历。
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, amount);
        return cal.getTime();
    }

    /**
     * 分加减
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date minuteOperation(Date date, int amount) {
        Calendar cal = Calendar.getInstance();//使用默认时区和语言环境获得一个日历。
        cal.setTime(date);
        cal.add(Calendar.MINUTE, amount);
        return cal.getTime();
    }

    /**
     * 转换Date为LocalDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * 转换Date为LocalDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime toLocalDateTime(String date) {
        return dateStrToDate(date).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * 转换Date为LocalDate
     *
     * @param date
     * @return
     */
    public static LocalDate toLocalDate(Date date) {
        if (Objects.isNull(date)) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * 转换LocalDateTime为Date
     *
     * @param localDateTime
     * @return
     */
    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 转换LocalDate为Date
     *
     * @param localDate
     * @return
     */
    public static Date toDate(LocalDate localDate) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        return Date.from(instant);
    }

    /**
     * 判断当前日期是否介于指定日期范围内
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static Boolean betweenDate(LocalDate startDate, LocalDate endDate) {
        if (LocalDate.now().equals(startDate))
            return true;
        if (LocalDate.now().equals(endDate))
            return true;
        if (LocalDate.now().isAfter(startDate) && LocalDate.now().isBefore(endDate))
            return true;
        return false;
    }

    /**
     * 判断当前时间是否介于指定时间范围内
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static Boolean betweenTime(LocalTime startTime, LocalTime endTime) {
        return LocalTime.now().compareTo(startTime) > -1 && LocalTime.now().compareTo(endTime) < 1;
    }

    /**
     * 判断当前时刻是否介于指定时刻范围内
     *
     * @param fromDateTime
     * @param toDateTime
     * @return
     */
    public static Boolean betweenDateTime(LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        return LocalDateTime.now().compareTo(fromDateTime) > -1 && LocalDateTime.now().compareTo(toDateTime) < 1;
    }

    /**
     * 今天还有多少秒
     *
     * @param
     * @return
     */
    public static Long dayRemainingSeconds() {
        LocalDateTime midnight = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        return ChronoUnit.SECONDS.between(LocalDateTime.now(), midnight);
    }

    public static LocalDate parseYmd(String dateStr) {
        if (StringUtils.isBlank(dateStr))
            return null;
        return LocalDate.parse(dateStr, ymd);
    }

    public static LocalDateTime parseYmdhms(String dateStr) {
        if (StringUtils.isBlank(dateStr))
            return null;
        return LocalDateTime.parse(dateStr, ymdhms);
    }

    public static long diffDays(LocalDateTime beforeDate, LocalDateTime afterDate) {
        return ChronoUnit.DAYS.between(beforeDate, afterDate);
    }

    public static LocalDateTime getDateTimeOfTimestamp(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    public static long getTimestamp(LocalDateTime localDateTime) {
        return localDateTime.toEpochSecond(ZoneOffset.of("+8"));
    }

    public static long getTimestampMilliSecond(LocalDateTime localDateTime) {
        return localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    //获取yyyy-mm-dd HH:mm:ss格式当前时间
    public static LocalDateTime getCurrFormatLocalDateTime() {
        String currTime = LocalDateTime.now().format(ymdhms);
        return parseYmdhms(currTime);
    }

    public static Date getCurrFormatDateTime() {
        LocalDateTime currTime = getCurrFormatLocalDateTime();
        return toDate(currTime);
    }

    /**
     * 获取最近周几 的时间
     *
     * @param dictionaryWeek 周几
     * @return yyyy-MM-dd
     */
    public static LocalDate getNextWeekOnceDay(int dictionaryWeek) {
        int day = 0;
        int value = LocalDate.now().getDayOfWeek().getValue();
        if (dictionaryWeek == value) {
            day = 7;
        }
        if (value < dictionaryWeek) {
            day = dictionaryWeek - value;
        }
        if (value > dictionaryWeek) {
            day = 7 - (value - dictionaryWeek);
        }
        return LocalDate.now().plusDays(day);
    }

    /**
     * 获取最近周几时分秒的时间
     *
     * @param dictionaryWeek 周几
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static LocalDateTime getNextWeekNearOnceDay(int dictionaryWeek, LocalTime localTime) {
        int day = 7;
        int week = LocalDate.now().getDayOfWeek().getValue();
        if (week < dictionaryWeek) {
            day = dictionaryWeek - week;
        }
        if (week == dictionaryWeek && LocalTime.now().isBefore(localTime)) {
            return LocalDateTime.of(LocalDate.now(), localTime);
        }
        if (week > dictionaryWeek) {
            day = 7 - (week - dictionaryWeek);
        }
        return LocalDateTime.of(LocalDate.now().plusDays(day), localTime);
    }

    public static String format(Date date) {
        try {
            return new SimpleDateFormat(strYmdHms).format(date);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}