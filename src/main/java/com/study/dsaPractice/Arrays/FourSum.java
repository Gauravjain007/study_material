package com.study.dsaPractice.Arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Problem: https://leetcode.com/problems/4sum/
 */
public class FourSum {

    /**
     * Finds all quadruplets in the given array that sum up to the given target.
     * Uses the two-pointer technique to find all possible quadruplets.
     * <p>
     * Time Complexity: O(N^3)
     * <p>
     * Space Complexity: O(1) [ignoring the space used to store the result]
     * <p>
     * 
     * @param nums   the input array
     * @param target the target sum
     * @return all quadruplets in the given array that sum up to the given target
     */
    public static List<List<Integer>> fourSum(int[] nums, int target) {
        int n = nums.length;
        if (n < 4)
            return List.of();

        List<List<Integer>> res = new ArrayList<>();

        Arrays.sort(nums);

        for (int i = 0; i < n - 3; i++) {
            if (i > 0 && nums[i] == nums[i - 1])
                continue;
            for (int j = i + 1; j < n - 2; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1])
                    continue;

                long target2 = (long) target - nums[i] - nums[j];
                movePointer(nums, target2, i, j, res);
            }
        }
        return res;
    }

    private static void movePointer(int[] nums, long target, int i, int j, List<List<Integer>> res) {
        int k = j + 1;
        int l = nums.length - 1;

        while (k < l) {
            long sum = (long) nums[k] + nums[l];
            if (sum == target) {
                res.add(List.of(nums[i], nums[j], nums[k++], nums[l--]));
                while (k < l && nums[k] == nums[k - 1])
                    k++;
                while (k < l && nums[l] == nums[l + 1])
                    l--;
            } else if (sum > target)
                l--;
            else
                k++;
        }
    }

    public static void main(String[] args) {
        System.out.println(fourSum(new int[] { 1, 0, -1, 0, -2, 2 }, 0));
        System.out.println(fourSum(new int[] { 2, 2, 2, 2, 2 }, 8));
    }
}
