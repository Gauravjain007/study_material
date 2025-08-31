package com.study.dsaPractice.Arrays;

public class MaximumConsecutiveOnes {
    public static int findMaximumConsecutiveOnes(int[] nums) {
        int maxCnt = 0;
        int cnt = 0;
        for (int num : nums) {
            if (num == 1) {
                cnt++;
                if (cnt > maxCnt) {
                    maxCnt = cnt;
                }
            } else {
                cnt = 0;
            }
        }
        return maxCnt;
    }

    public static void main(String[] args) {
        int[] nums = { 1, 1, 0, 1, 1, 1 };
        System.out.println(findMaximumConsecutiveOnes(nums));
    }
}
