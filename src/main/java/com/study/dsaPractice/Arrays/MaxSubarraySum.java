package com.study.dsaPractice.Arrays;

import java.util.Arrays;

/**
 * Problem: https://leetcode.com/problems/maximum-subarray/
 * 
 * <p>
 * Approach: Kadane's Algorithm
 * <p>
 * Time Complexity: O(n)
 * <p>
 * Space Complexity: O(1)
 */
public class MaxSubarraySum {
    /**
     * Algorithm: Kadane's Algorithm
     * <ul>
     * <li>The algorithm maintains a current sum and a maximum sum.
     * <li>It iterates through the array, updating the current sum by adding the
     * current element to it.
     * <li>If the current sum becomes negative, it resets it to zero.
     * <li>The maximum sum is updated whenever the current sum exceeds it.
     * </ul>
     * 
     * @param nums
     * @return
     */
    public static int maxSubArraySum(int[] nums) {
        int maxSum = nums[0];
        int sum = 0;
        for (int num : nums) {
            sum += num;
            maxSum = Math.max(sum, maxSum);
            if (sum < 0)
                sum = 0;
        }
        return maxSum;
    }

    public static int[] maxSubArray(int[] nums) {
        int start = 0;
        int end = 0;
        int tempStart = 0;

        int maxSum = nums[0];
        int sum = nums[0];

        for (int i = 1; i < nums.length; i++) {
            // If adding the current element to the existing sum is less than the current
            // element itself, start a new subarray from the current element
            if (sum + nums[i] < nums[i]) {
                sum = nums[i];
                tempStart = i;
            } else {
                sum += nums[i];
            }

            // If current sum is greater than maxSum, then update maxSum and the start and
            // end indices of the maximum subarray
            if (sum > maxSum) {
                maxSum = sum;
                start = tempStart;
                end = i;
            }
        }

        int[] result = new int[end - start + 1];
        for (int i = 0; i < end - start + 1; i++) {
            result[i] = nums[start + i];
        }
        return result;
    }

    public static void main(String[] args) {
        int[] nums = { -2, 1, -3, 4, -1, 2, 1, -5, 4 };
        System.out.println("Max subarray sum: " + maxSubArraySum(nums));
        System.out.println("Max subarray elements: " + Arrays.toString(maxSubArray(nums)));
    }
}
