package com.study.dsaPractice.Arrays;

import java.util.Arrays;

/**
 * Problem: https://leetcode.com/problems/rearrange-array-elements-by-sign/
 */
public class RearrangeArrayBySign {
    public static int[] rearrangeArray(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];

        int posi = 0;
        int negi = 1;

        for (int num : nums) {
            if (num > 0) {
                ans[posi] = num;
                posi += 2;
            } else {
                ans[negi] = num;
                negi += 2;
            }
        }

        return ans;
    }

    public static void main(String[] args) {
        int[] nums = { 3, 1, -2, -5, 2, -4 };
        int[] result = rearrangeArray(nums);
        System.out.println("Rearranged Array: " + Arrays.toString(result));
    }
}
