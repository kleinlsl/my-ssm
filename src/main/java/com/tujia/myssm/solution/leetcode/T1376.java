package com.tujia.myssm.solution.leetcode;

import java.util.Arrays;

/**
 *
 * @author: songlinl
 * @create: 2023/5/1 16:23
 */
public class T1376 {

    /**
     *
     * @param n
     * @param headID
     * @param manager
     * @param informTime
     * @return
     */
    public int numOfMinutes(int n, int headID, int[] manager, int[] informTime) {
        int maxTime = 0;
        int[] sumTime = new int[n];
        Arrays.fill(sumTime, -1);
        for (int i = 0; i < n; i++) {
            findMaxTime(i, manager, informTime, sumTime);
        }
        for (int i = 0; i < n; i++) {
            if (maxTime < sumTime[i]) {
                maxTime = sumTime[i];
            }
        }
        return maxTime;
    }

    public int findMaxTime(int idx, int[] manager, int[] informTime, int[] sumTime) {
        if (manager[idx] == -1) {
            sumTime[idx] = informTime[idx];
            return informTime[idx];
        }
        if (sumTime[idx] != -1) {
            return sumTime[idx];
        }
        int time = findMaxTime(manager[idx], manager, informTime, sumTime) + informTime[idx];
        sumTime[idx] = time;
        return time;
    }
}
