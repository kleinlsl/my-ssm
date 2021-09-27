package com.tujia.myssm.common.utils.serializer;

import java.io.IOException;
import java.time.LocalDateTime;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 *
 * @author: songlinl
 * @create: 2021/09/26 14:26
 */
public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String dateTimeAsString = value.format(DateTimeFormatterConstant.FORMATTER_4Y2M2D_T_2H2M2S);
        gen.writeString(dateTimeAsString);
    }
}