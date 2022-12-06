package com.tujia.myssm.common.utils.serializer;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.tujia.myssm.api.model.date.LocalDateRangeSet;

/**
 *
 * @author: songlinl
 * @create: 2022/12/05 17:11
 */
public class LocalDateRangeSetSerializer {

    public static class Deserializer extends JsonDeserializer<LocalDateRangeSet> {
        @Override
        public LocalDateRangeSet deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            JsonNode node = jsonParser.getCodec().readTree(jsonParser);
            String s = node.asText();
            return LocalDateRangeSet.parse(s);
        }
    }

    public static class Serializer extends JsonSerializer<LocalDateRangeSet> {
        @Override
        public void serialize(LocalDateRangeSet value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeString(value.toString());
        }
    }
}
