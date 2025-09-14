package com.study.dsaPractice.Arrays;

import java.util.HashMap;
import java.util.Map;

/**
 * Problem: https://leetcode.com/problems/subarray-sum-equals-k/
 */
public class SubarraySumEqualsK {

    /**
     * <b>Algorithm: Prefix Sum</b>
     * <p>
     * Finds the number of subarrays with sum equal to k.
     * Uses a HashMap to store the cumulative sum of the array elements.
     * <p>
     * Time Complexity: O(n), Space Complexity: O(n)
     * 
     * @param nums the input array
     * @param k    the target sum
     * @return the number of subarrays with sum equal to k
     */
    public static int subarraySum(int[] nums, int k) {

        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);

        int count = 0;
        int sum = 0;

        for (int num : nums) {
            sum += num;
            count += map.getOrDefault(sum - k, 0);
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return count;
    }

    public static void main(String[] args) {
        int[] nums = { 1, 1, 1 };
        int k = 2;
        System.out.println("Total subarrays: " + subarraySum(nums, k));
    }
}
