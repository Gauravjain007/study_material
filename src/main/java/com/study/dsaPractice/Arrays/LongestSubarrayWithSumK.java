package com.study.dsaPractice.Arrays;

import java.util.HashMap;
import java.util.Map;

/**
 * Problem:
 * https://takeuforward.org/data-structure/longest-subarray-with-given-sum-k/
 */
public class LongestSubarrayWithSumK {

    /**
     * Finds the length of the longest subarray with sum equal to k.
     * Works for arrays with both positive and negative numbers.
     * 
     * @param nums the input array
     * @param k    the target sum
     * @return the length of the longest subarray with sum equal to k
     */
    public static int findLongestSubarrayWithSumK(int[] nums, int k) {
        int n = nums.length;
        int maxLength = 0;
        int prefixSum = 0;
        Map<Integer, Integer> prefixSumIndexMap = new HashMap<>();

        for (int i = 0; i < n; i++) {
            prefixSum += nums[i];

            if (prefixSum == k) {
                maxLength = i + 1;
            }

            if (!prefixSumIndexMap.containsKey(prefixSum)) {
                prefixSumIndexMap.put(prefixSum, i);
            }

            if (prefixSumIndexMap.containsKey(prefixSum - k)) {
                int prevIndex = prefixSumIndexMap.get(prefixSum - k);
                maxLength = Math.max(maxLength, i - prevIndex);
            }
        }
        return maxLength;
    }

    /**
     * Finds the length of the longest subarray with sum equal to k.
     * Works only for arrays with non-negative numbers.
     * 
     * @param nums the input array
     * @param k    the target sum
     * @return the length of the longest subarray with sum equal to k
     */
    public static int findLongestSubarrayWithSumK2(int[] nums, int k) {
        int n = nums.length;
        int maxLen = 0;
        int currSum = 0;
        int left = 0;
        int right = 0;
        while (right < n) {
            currSum += nums[right];
            while (currSum > k && left <= right) {
                currSum -= nums[left];
                left++;
            }
            if (currSum == k) {
                maxLen = Math.max(maxLen, right - left + 1);
            }

            right++;
        }

        return maxLen;
    }

    /**
     * Finds the length of the longest subarray with sum equal to zero.
     * 
     * @param nums the input array
     * @return the length of the longest subarray with sum equal to zero
     */
    public static int findLongestSubArrayWithZeroSum(int[] nums) {
        int maxLength = 0;
        Map<Integer, Integer> sumMap = new HashMap<>();
        int prefixSum = 0;

        for (int i = 0; i < nums.length; i++) {
            prefixSum += nums[i];

            if (prefixSum == 0) {
                maxLength = i + 1;
            }

            if (sumMap.containsKey(prefixSum)) {
                maxLength = Math.max(maxLength, i - sumMap.get(prefixSum));
            } else {
                sumMap.put(prefixSum, i);
            }
        }

        return maxLength;
    }

    public static void main(String[] args) {
        int[] nums1 = { 1, -1, 6, 5, -2, 1, 3, -3, 0, 4 };
        int k = 3;
        System.out.println(findLongestSubarrayWithSumK(nums1, k));

        int[] nums2 = { 4, 1, 1, 1, 2, 3, 5 }; // only non-negative numbers
        k = 5;
        System.out.println(findLongestSubarrayWithSumK2(nums2, k));

        int[] nums3 = { 9, -3, 3, -1, 6, -5 };
        System.out.println(findLongestSubArrayWithZeroSum(nums3));
    }
}
