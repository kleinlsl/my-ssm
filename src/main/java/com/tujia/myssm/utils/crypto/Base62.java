package com.tujia.myssm.utils.crypto;

import java.util.Arrays;
import java.util.List;

/**
 * @author: hongliu_6
 * @create: 2020/9/1 12:58
 * @description: Base62 编解码工具
 */
public class Base62 {
    private static final List<Character> toBase62 = Arrays.asList('c', 'C', 'B', 'f', 'E', 'G', 'F', 'H', 'j', 'J', 'X', 'L', 'Z', 'N', 'O', 'P', 'Q',
                                                                  'R', 'S', 'T', 'U', 'V', 'W', 'K', 'Y', 'M', 'a', 'b', 'A', 'd', 'e', 'D', 'g', 'h',
                                                                  'i', 'I', 'k', 'l', 'y', 'n', 'o', 'p', 'q', 'r', 's', 'u', 't', 'v', 'w', 'x', 'm',
                                                                  'z', '0', '9', '2', '3', '6', '5', '4', '8', '7', '1');
    private static int scale = toBase62.size();

    /**
     * 将数字转为62进制
     *
     * @param num Long 型数字
     * @return 62进制字符串
     */
    public static String encode(long num) {
        StringBuilder sb = new StringBuilder();
        while (num > scale - 1) {
            sb.append(toBase62.get((int) (num % scale)));
            num = num / scale;
        }
        sb.append(toBase62.get((int) num));
        return sb.reverse().toString();
    }

    /**
     * 62进制字符串转为数字
     *
     * @param str 编码后的62进制字符串
     * @return 解码后的 10 进制字符串
     */
    public static long decode(String str) {
        //        将 0 开头的字符串进行替换
        str = str.replace("^0*", "");
        long num = 0;
        int index = 0;
        for (int i = 0; i < str.length(); i++) {
            /**
             * 查找字符的索引位置
             */
            index = toBase62.indexOf(str.charAt(i));
            /**
             * 索引位置代表字符的数值
             */
            num += (long) (index * (Math.pow(scale, str.length() - i - 1)));
        }
        return num;
    }

}