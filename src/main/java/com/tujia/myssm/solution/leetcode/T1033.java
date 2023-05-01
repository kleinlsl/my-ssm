package com.tujia.myssm.solution.leetcode;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author: songlinl
 * @create: 2023/4/30 17:54
 */
public class T1033 {
    public static void main(String[] args) {
        T1033 t = new T1033();
        Scanner sc = new Scanner(System.in);
        int a = sc.nextInt();
        int b = sc.nextInt();
        int c = sc.nextInt();
        System.out.print(Arrays.toString(t.numMovesStones(a, b, c)));
    }

    public int[] numMovesStones(int a, int b, int c) {
        int[] result = new int[2];
        List<Integer> arr = Arrays.asList(a, b, c);
        arr.sort(Comparator.naturalOrder());
        a = arr.get(0);
        b = arr.get(1);
        c = arr.get(2);
        result[0] = calMinMoves(a, b, c);
        result[1] = calMaxMoves(a, b, c);
        return result;
    }

    private int calMinMoves(int a, int b, int c) {
        int left = b - a;
        int right = c - b;
        if (left == 1 && right == 1) {
            return 0;
        }
        if (left == 2 || right == 2) {
            return 1;
        }
        if (left > 1 && right > 1) {
            return 2;
        }
        return 1;
    }

    private int calMaxMoves(int a, int b, int c) {
        return (c - b) + (b - a) - 2;
    }
}
