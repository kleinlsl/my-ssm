package com.tujia.myssm.common.utils.serializer;

import java.time.format.DateTimeFormatter;

/**
 *
 * @author: songlinl
 * @create: 2021/09/27 11:37
 */
public class DateTimeFormatterConstant {
    public static final DateTimeFormatter FORMATTER_4Y_2M_2D = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter FORMATTER_2H_2M_2S = DateTimeFormatter.ofPattern("HH:mm:ss");
    public static final DateTimeFormatter FORMATTER_4Y2M2D_2H2M2S = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter FORMATTER_4Y2M2D_T_2H2M2S = DateTimeFormatter
            .ofPattern("yyyy-MM-dd'T'HH:mm:ss");
}
