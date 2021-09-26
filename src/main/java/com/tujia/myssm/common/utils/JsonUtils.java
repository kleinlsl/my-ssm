package com.tujia.myssm.common.utils;

import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.tujia.myssm.common.utils.serializer.LocalDateTimeDeserializer;
import com.tujia.myssm.common.utils.serializer.LocalDateTimeSerializer;

/**
 * @author: songlinl
 * @create: 2021/09/08 15:25
 */
public final class JsonUtils {
    private static final Logger log = LoggerFactory.getLogger(JsonUtils.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        SimpleModule localDateTimeModel = new SimpleModule();
        localDateTimeModel.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        localDateTimeModel.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());

        objectMapper.registerModule(localDateTimeModel);
    }

    public static <T> String tryToJson(T object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            log.error("serialize the object to string error, the class is " + object.getClass().getCanonicalName(), e);
        }
        return null;
    }

    public static <T> String toJson(T object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            log.error("serialize the object to string error, the class is " + object.getClass().getCanonicalName(), e);
            throw new RuntimeException(e);
        }
    }

    public static <T> byte[] toBytes(T object) {
        try {
            return objectMapper.writeValueAsBytes(object);
        } catch (Exception e) {
            log.error("serialize the object to bytes error, the class is " + object.getClass().getCanonicalName(), e);
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String value = JsonUtils.toJson(LocalDateTime.now());
        System.out.println("value = " + value);
    }
}
