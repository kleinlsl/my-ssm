package com.tujia.myssm.solution.leetcode;

/**
 * todo 未通过
 * @author: songlinl
 * @create: 2023/5/1 16:53
 */
public class T665 {
    public boolean checkPossibility(int[] nums) {
        int flag = 0;
        if (nums.length < 2) {
            return true;
        }
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] > nums[i + 1]) {
                flag++;
                if (i > 0 && nums[i - 1] > nums[i + 1]) {
                    flag++;
                }
            }
            if (flag > 1) {
                return false;
            }
        }
        return flag == 1;
    }
}
