package com.tujia.myssm.web.utils;

/**
 *
 * @author: songlinl
 * @create: 2022/08/25 10:34
 */
public class EmojiUtils {

    public static String filterUtf8Mb4(String source) {
        int LAST_BMP = 0xFFFF;
        StringBuilder builder = new StringBuilder(source.length());
        for (int i = 0; i < source.length(); i++) {
            int codePoint = source.charAt(i);
            if (codePoint < LAST_BMP && codePoint > 0) {
                builder.appendCodePoint(codePoint);
            } else {
                System.out.println(codePoint + "  ==  " + source.charAt(i));
                System.out.println();
            }
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        StringBuilder builder = new StringBuilder();
        builder.appendCodePoint(0xFFFF);
        System.out.println(builder.toString());
    }
}
