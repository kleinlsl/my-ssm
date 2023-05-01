package com.tujia.myssm.solution.leetcode;

import java.util.Scanner;

/**
 *
 * @author: songlinl
 * @create: 2023/5/1 9:18
 */
public class T41 {

    public int firstMissingPositive(int[] nums) {
        int[] arr = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            int index = nums[i];
            if (index <= 0 || index > nums.length) {
                continue;
            }
            arr[index - 1] = 1;
        }
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 0) {
                return i + 1;
            }
        }
        return arr.length + 1;
    }
}
