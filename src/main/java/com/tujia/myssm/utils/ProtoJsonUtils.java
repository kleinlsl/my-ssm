package com.tujia.myssm.utils;

import java.io.IOException;
import com.google.protobuf.Message;
import com.google.protobuf.util.JsonFormat;

/**
 *
 * @author: songlinl
 * @create: 2022/08/16 16:22
 */
public class ProtoJsonUtils {
    public static String toJson(Message sourceMessage) throws IOException {
        String json = JsonFormat.printer().print(sourceMessage);
        return json;
    }

    public static Message toProtoBean(Message.Builder targetBuilder, String json) throws IOException {
        JsonFormat.parser().merge(json, targetBuilder);
        return targetBuilder.build();
    }
}
