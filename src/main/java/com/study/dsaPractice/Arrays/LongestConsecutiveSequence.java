package com.study.dsaPractice.Arrays;

import java.util.Arrays;

/**
 * Problem: https://leetcode.com/problems/longest-consecutive-sequence/
 */
public class LongestConsecutiveSequence {

    /**
     * Finds the length of the longest consecutive sequence in an array of integers.
     * 
     * @param nums the input array
     * @return the length of the longest consecutive sequence
     */
    public static int longestConsecutive(int[] nums) {
        if (nums == null || nums.length == 0)
            return 0;

        // Sort the array in ascending order
        Arrays.sort(nums);

        int maxLength = 1;
        int currLen = 1;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i - 1] == nums[i]) {
                // If the current element is equal to the previous element, skip
                continue;
            }
            if (nums[i - 1] + 1 == nums[i]) {
                // If the current element is consecutive to the previous element, update the
                // current length
                currLen++;
            } else {
                // If the current element is not consecutive to the previous element, update the
                // maxLength and reset the current length
                maxLength = Math.max(maxLength, currLen);
                currLen = 1;
            }
        }

        return Math.max(maxLength, currLen);
    }

    public static void main(String[] args) {
        int[] nums = { 100, 4, 200, 1, 3, 2, 2 };
        System.out.println(longestConsecutive(nums));
    }
}
