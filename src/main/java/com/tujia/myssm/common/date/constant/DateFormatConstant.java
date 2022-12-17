package com.tujia.myssm.common.date.constant;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

/**
 *
 * @author: songlinl
 * @create: 2021/09/27 11:37
 */
public class DateFormatConstant {
    private DateFormatConstant() {
    }

    public static final DateTimeFormatter FORMATTER_4Y_2M_2D = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter FORMATTER_2H_2M_2S = DateTimeFormatter.ofPattern("HH:mm:ss");
    public static final DateTimeFormatter FORMATTER_4Y2M2D_2H2M2S = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter FORMATTER_4Y2M2D_T_2H2M2S = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public static final ZoneId ZONE_ID_G8 = TimeZone.getTimeZone("GMT+8").toZoneId();
    public static final DateTimeFormatter FORMATTER_4Y2M2D_2H2M2S_G8 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZONE_ID_G8);
    public static final DateTimeFormatter FORMATTER_4Y_2M_2D_G8 = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZONE_ID_G8);
    public static final DateTimeFormatter FORMATTER_4Y2M2D_G8 = DateTimeFormatter.ofPattern("yyyyMMdd").withZone(ZONE_ID_G8);

}
