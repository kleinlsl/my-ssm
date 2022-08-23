package com.tujia.myssm.utils;

import java.util.Arrays;

/**
 *
 * @author: songlinl
 * @create: 2022/08/17 16:11
 */
public class ByteUtils {
    public static String toByteString(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (int i = 0; i < bytes.length; i++) {
            builder.append(bytes[i]);
            if (i != bytes.length - 1) {
                builder.append(",");
            }
        }
        builder.append("] << len:").append(bytes.length);
        return builder.toString();
    }

    public static boolean equals(byte[] a, byte[] b) {
        return Arrays.equals(a, b);
    }
}
