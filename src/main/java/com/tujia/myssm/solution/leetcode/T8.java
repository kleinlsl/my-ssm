package com.tujia.myssm.solution.leetcode;

import java.util.Scanner;

/**
 *
 * @author: songlinl
 * @create: 2023/4/30 18:48
 */
public class T8 {
    public static void main(String[] args) {
        T8 t = new T8();
        Scanner sc = new Scanner(System.in);
        System.out.print((t.myAtoi(sc.nextLine())));
    }

    /**
     * 00-00
     * 00098
     * aa-09
     * step ：
     * 1. 符号判断
     * 2.
     * @param s
     * @return
     */
    public int myAtoi(String s) {
        int sign = 1;   // 符号位
        int bit = 0;   // 有效数字位数
        long res = 0;   // 结果值
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (bit == 0 && c == ' ') {         //前导空格
                continue;
            }
            if (bit == 0 && (c == '-' || c == '+')) {  //符号
                if (c == '-') {
                    sign = -1;
                    bit++;
                }
                if (c == '+') {
                    sign = 1;
                    bit++;
                }
            } else if (c >= '0' && c <= '9') { // 数字
                bit++;
                int v = c - '0';
                res = (res * 10) + v;
                if (sign > 0 && sign * res > Integer.MAX_VALUE) {
                    return Integer.MAX_VALUE;
                }
                if (sign < 0 && sign * res < Integer.MIN_VALUE) {
                    return Integer.MIN_VALUE;
                }
            } else {
                break;
            }
        }
        return (int) (res * sign);
    }
}
