package com.study.dsaPractice.Arrays;

import java.util.Arrays;

/**
 * Problem:
 * https://leetcode.com/problems/sort-colors/description/
 */
public class SortThreeValuedArray {

    /**
     * Sorts an array of 0s, 1s, and 2s in a single pass, using the Dutch National
     * Flag algorithm.
     *
     * @param nums The array to be sorted.
     */
    public static void sortArray(int[] nums) {
        int low = 0;
        int mid = 0;
        int high = nums.length - 1;
        while (mid <= high) {
            if (nums[mid] == 2) {
                nums[mid] = nums[high];
                nums[high--] = 2;
            } else if (nums[mid] == 1) {
                mid++;
            } else {
                nums[mid++] = nums[low];
                nums[low++] = 0;
            }
        }
    }

    public static void main(String[] args) {
        int[] nums = { 2, 0, 2, 1, 1, 0 };
        System.out.println("Original Array: " + Arrays.toString(nums));
        sortArray(nums);
        System.out.println("Sorted Array: " + Arrays.toString(nums));
    }
}
