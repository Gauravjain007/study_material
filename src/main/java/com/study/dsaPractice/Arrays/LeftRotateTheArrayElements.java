package com.study.dsaPractice.Arrays;

/**
 * Given an array, rotate the array to the left by k steps, where k is
 * non-negative.
 * Reversal Algorithm:
 * Reversal algorithm is based on the idea that a left rotation by k is
 * equivalent to:
 * <ul>
 * <li>Moving the first k elements to the end, and shifting the rest to the
 * front.
 * <li>By reversing the first k elements, then the rest, and finally the whole
 * array, you effectively rotate the array in-place.
 * </ul>
 */
public class LeftRotateTheArrayElements {

    /**
     * Left rotates the array by k steps in-place using the reversal
     * algorithm.
     * <ul>
     * <li>Reverse the first k elements
     * <li>Reverse the remaining elements
     * <li>Reverse the whole array
     * </ul>
     * <p>
     * Time Complexity: O(n)
     * <p>
     * Space Complexity: O(1)
     *
     * @param nums the array to be rotated
     * @param k    the number of positions to rotate the array to the left
     */
    public static void leftRotateTheArrayElements(int[] nums, int k) {
        if (nums.length == 0 || k <= 0)
            return;

        k = k % nums.length;
        reverse(nums, 0, k - 1);
        reverse(nums, k, nums.length - 1);
        reverse(nums, 0, nums.length - 1);
    }

    private static void reverse(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }
    }

    public static void main(String[] args) {
        int[] nums = { 1, 2, 3, 4, 5, 6 };
        int k = 4;
        leftRotateTheArrayElements(nums, k);
        for (int n : nums) {
            System.out.print(n + " ");
        }
    }
}
