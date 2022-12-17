package com.tujia.myssm.common.date;

import java.time.LocalDateTime;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tujia.myssm.common.date.serializer.LocalDateTimeDeserializer;
import com.tujia.myssm.common.date.serializer.LocalDateTimeSerializer;
import lombok.Data;

/**
 *
 * @author: songlinl
 * @create: 2021/09/28 15:04
 */
@Data
public class DateTimeRange {
    /**
     * 开始
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime start;
    /**
     * 结束
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime end;

    /**
     * 当前时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date currentTime = new Date();
}
