package com.tujia.myssm.solution.leetcode;

import java.util.Scanner;

/**
 * todo 未通过
 * @author: songlinl
 * @create: 2023/5/1 9:31
 */
public class T44 {
    public static void main(String[] args) {
        T44 t = new T44();
        Scanner sc = new Scanner(System.in);
        System.out.println(t.isMatch(sc.next(), sc.next()));
    }

    public boolean isMatch(String s, String p) {
        if (s.length() == 0 && p.length() == 0) {
            return true;
        }
        int[][] arr = new int[s.length() + 1][p.length() + 1];
        //        for (int i = 0; i < s.length(); i++) {
        //            arr[i][0] = 1;
        //        }
        for (int j = 1; j <= p.length(); j++) {
            char pc = p.charAt(j - 1);
            if (pc == '*') {
                arr[0][j] = 1;
            }
        }
        arr[0][0] = 1;
        for (int j = 1; j <= p.length(); j++) {
            for (int i = 1; i <= s.length(); i++) {
                char pc = p.charAt(j - 1);
                char sc = s.charAt(i - 1);
                if (isMatch(sc, pc)) {
                    if (arr[i - 1][j - 1] == 1) {
                        arr[i][j] = 1;
                    }
                    if (pc == '*') {
                        if (arr[i - 1][j] == 1) {
                            arr[i][j] = 1;
                        }
                    }
                }
            }
        }
        return arr[s.length()][p.length()] == 1;
    }

    private boolean isMatch(char sc, char pc) {
        if (pc == '?' || pc == '*') {
            return true;
        }
        if (sc == pc) {
            return true;
        }
        return false;
    }
}
