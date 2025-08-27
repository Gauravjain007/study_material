package com.study.dsaPractice.Arrays;

import java.util.Arrays;

/**
 * Problem:
 * https://leetcode.com/problems/move-zeroes/description/
 */
public class MoveZerosToEnd {

    /**
     * Given an array of integers, move all 0s to the end of the array, while
     * preserving the relative order of non-zero elements.
     * <p>
     * This algorithm iterates through the array, keeping track of the first 0
     * encountered. When a non-zero element is found, it is swapped with the
     * first 0 element. This ensures that all 0s are moved to the end of the
     * array while preserving the relative order of non-zero elements.
     * </p>
     */
    public static void moveAllZerosToArrayEnd(int[] nums) {
        int i = -1;
        for (int j = 0; j < nums.length; j++) {
            if (nums[j] == 0) {
                if (i == -1 || nums[i] != 0)
                    i = j;
            } else if (i > -1 && nums[i] == 0) {
                nums[i] = nums[j];
                nums[j] = 0;
                i++;
            }
        }
    }

    /**
     * Given an array of integers, move all 0s to the end of the array, while
     * preserving the relative order of non-zero elements.
     * <p>
     * This algorithm iterates through the array, inserting all non-zero elements
     * at the front of the array. Once all non-zero elements have been inserted,
     * the remaining elements are filled with 0s.
     * </p>
     */
    public static void moveAllZerosToArrayEnd2(int[] nums) {
        int insertPos = 0;
        for (int num : nums) {
            if (num != 0) {
                nums[insertPos++] = num;
            }
        }
        while (insertPos < nums.length) {
            nums[insertPos++] = 0;
        }
    }

    public static void main(String[] args) {
        int[] nums = { 1, 0, 2, 0, 0, 5, 5, 0 };
        moveAllZerosToArrayEnd(nums);
        System.out.println("After moving all zeros to end: " + Arrays.toString(nums));
    }
}
