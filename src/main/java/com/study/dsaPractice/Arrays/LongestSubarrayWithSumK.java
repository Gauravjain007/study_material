package com.study.dsaPractice.Arrays;

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
        java.util.Map<Integer, Integer> prefixSumIndexMap = new java.util.HashMap<>();

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

    public static void main(String[] args) {
        int[] nums = { 1, -1, 6, 5, -2, 1, 3, -3, 0, 4 };
        int k = 3;
        System.out.println(findLongestSubarrayWithSumK(nums, k));
        System.out.println(findLongestSubarrayWithSumK2(nums, k));
    }
}
