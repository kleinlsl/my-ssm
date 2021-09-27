package com.tujia.myssm.common.utils.serializer;

import java.io.IOException;
import java.time.LocalDateTime;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

/**
 *
 * @author: songlinl
 * @create: 2021/09/26 14:25
 */
public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String s = node.asText();
        LocalDateTime parse = LocalDateTime.parse(s, DateTimeFormatterConstant.FORMATTER_4Y2M2D_T_2H2M2S);
        return parse;
    }
}