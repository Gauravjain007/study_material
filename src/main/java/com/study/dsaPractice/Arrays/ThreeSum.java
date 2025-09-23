package com.study.dsaPractice.Arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Problem: https://leetcode.com/problems/3sum/
 */
public class ThreeSum {

    /**
     * Finds all triplets in the given array that sum up to 0.
     * Uses the two-pointer technique to find all possible triplets.
     * <p>
     * Time Complexity: O(NlogN) + O(N^2)
     * <p>
     * Space Complexity: O(1) [ignoring the space used to store the result]
     * <p>
     * 
     * @param nums the input array
     * @return all triplets in the given array that sum up to 0
     */
    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        int n = nums.length;
        if (n < 3)
            return res;

        Arrays.sort(nums);

        for (int i = 0; i < n - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1])
                continue; // Skip duplicate i

            int left = i + 1;
            int right = n - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum == 0) {
                    res.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    left = movePointer(nums, left, right);
                } else if (sum < 0) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        return res;
    }

    /**
     * Move the pointer to the right until it points to a different element.
     * 
     * @param nums  the input array
     * @param left  the starting index of the pointer
     * @param right the ending index of the pointer
     * @return the new index of the pointer
     */
    private static int movePointer(int[] nums, int left, int right) {
        left++;
        while (left < right && nums[left] == nums[left - 1]) {
            left++;
        }
        return left;
    }

    public static void main(String[] args) {
        System.out.println(threeSum(new int[] { -1, 0, 1, 2, -1, -4 }));
    }
}
