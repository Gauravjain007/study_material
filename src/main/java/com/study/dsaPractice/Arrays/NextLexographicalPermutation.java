package com.study.dsaPractice.Arrays;

import java.util.Arrays;

/**
 * Problem: https://leetcode.com/problems/next-permutation/
 */
public class NextLexographicalPermutation {
    public static void nextPermutation(int[] nums) {
        int n = nums.length - 1;
        int i = n;
        // Find the first decreasing element from the end
        while (i > 0 && nums[i - 1] >= nums[i]) {
            i--;
        }

        // If the entire array is non-increasing, reverse it to get the smallest
        if (i == 0) {
            reverseArr(nums, 0, n);
            return;
        }

        int j = n;
        // Find the element just larger than nums[i-1] to swap with
        while (j >= i && nums[j] <= nums[i - 1]) {
            j--;
        }
        swap(nums, i - 1, j);
        // Reverse the elements after the position i-1 to get the next permutation
        reverseArr(nums, i, n);
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    private static void reverseArr(int[] arr, int start, int end) {
        while (start < end) {
            swap(arr, start, end);
            start++;
            end--;
        }
    }

    public static void main(String[] args) {
        int[] nums = { 1, 2, 3, 3, 2, 1 };
        nextPermutation(nums);
        // Output: [1, 3, 1, 2, 2, 3]
        System.out.println("Next Permutation: " + Arrays.toString(nums));
    }
}
