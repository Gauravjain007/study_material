package com.study.dsaPractice.Arrays;

/**
 * Problem: https://leetcode.com/problems/find-pivot-index/
 */
public class FindPivotIndex {

    /**
     * Finds the pivot index in the given array of integers.
     * The pivot index is the index at which the sum of elements to the left
     * of the index is equal to the sum of elements to the right of the index.
     * If no such index exists, returns -1.
     * 
     * @param nums the array of integers
     * @return the pivot index, or -1 if no such index exists
     */
    public static int pivotIndex(int[] nums) {
        int totalSum = 0;

        for (int num : nums) {
            totalSum += num;
        }

        int leftSum = 0;
        for (int i = 0; i < nums.length; i++) {
            if (leftSum == totalSum - nums[i] - leftSum)
                return i;
            leftSum += nums[i];
        }

        return -1;
    }

    public static void main(String[] args) {
        int[] nums = { 1, 7, 3, 6, 5, 6 };
        System.out.println(pivotIndex(nums));
    }
}
