package com.study.dsaPractice.Arrays;

import java.util.HashMap;
import java.util.Map;

/**
 * Problem:
 * https://leetcode.com/problems/two-sum/description/
 */
public class TwoSum {

    /**
     * Finds two elements in the given array that sum up to the given target k.
     * Best approach using HashMap.
     * <p>
     * Time Complexity: O(n), Space Complexity: O(n)
     * 
     * @param nums the input array
     * @param k    the target sum
     * @return two element indices whose values add up to k. Returns {-1, -1} if
     *         no such pair exists.
     */
    public static int[] twoSum(int[] nums, int k) {
        Map<Integer, Integer> hmap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (hmap.containsKey(k - nums[i])) {
                return new int[] { hmap.get(k - nums[i]), i };
            }

            hmap.put(nums[i], i);
        }
        return new int[] { -1, -1 };
    }

    /**
     * Finds two elements in the given array that sum up to the given target k.
     * Approaches using two pointers, does not use extra space.
     * <p>
     * Time Complexity: O(N) + O(N*logN). The loop will run at most N times. And
     * sorting the array will take N*logN time complexity.
     * <p>
     * Space Complexity: O(1)
     * 
     * @param nums the input array
     * @param k    the target sum
     * @return two element indices whose values add up to k. Returns {-1, -1} if
     *         no such pair exists.
     */
    public static int[] twoSum2(int[] nums, int k) {
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            int sum = nums[left] + nums[right];
            if (sum == k) {
                return new int[] { left, right };
            } else if (sum < k) {
                left++;
            } else {
                right--;
            }
        }
        return new int[] { -1, -1 };
    }

    public static void main(String[] args) {
        int[] nums = { 2, 7, 11, 15 };
        int target = 9;
        int[] result = twoSum(nums, target);
        System.out.println(
                "Indices of the two numbers that add up to " + target + ": [" + result[0] + ", " + result[1] + "]");
    }
}
