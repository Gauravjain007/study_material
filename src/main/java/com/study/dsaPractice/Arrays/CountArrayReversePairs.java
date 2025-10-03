package com.study.dsaPractice.Arrays;

/**
 * Problem: https://leetcode.com/problems/reverse-pairs/
 * 
 * A reverse pair is a pair of elements in the array that are in reverse order.
 * For example, given the array [1, 3, 2, 4], the reverse pairs are (3, 2) and
 * (4, 1).
 */
public class CountArrayReversePairs {

    /**
     * Counts the number of reverse pairs in the given array, using the merge sort
     * approach.
     * 
     * @param nums the array to count reverse pairs in
     * @param low  the starting index of the subarray to count in
     * @param mid  the ending index of the first half of the subarray to count in
     * @param high the ending index of the second half of the subarray to count in
     * @return the number of reverse pairs in the array
     */
    public static int countReversePairs(int[] nums, int low, int mid, int high) {
        int count = 0;
        int right = mid + 1;

        for (int left = low; left <= mid; left++) {
            while (right <= high && nums[left] > 2 * (long) nums[right]) {
                right++;
            }
            count += right - (mid + 1);
        }
        return count;
    }

    public static void merge(int[] nums, int low, int mid, int high) {
        int[] tmp = new int[high - low + 1];
        int i = 0;
        int left = low;
        int right = mid + 1;

        while (left <= mid && right <= high) {
            if (nums[left] > nums[right]) {
                tmp[i++] = nums[right++];
            } else {
                tmp[i++] = nums[left++];
            }
        }

        while (left <= mid)
            tmp[i++] = nums[left++];

        while (right <= high)
            tmp[i++] = nums[right++];

        System.arraycopy(tmp, 0, nums, low, tmp.length);
    }

    public static int mergeSort(int[] nums, int low, int high) {
        if (low >= high)
            return 0;
        int count = 0;
        int mid = low + (high - low) / 2;
        count += mergeSort(nums, low, mid);
        count += mergeSort(nums, mid + 1, high);
        // count the number of reverse pairs
        count += countReversePairs(nums, low, mid, high);
        merge(nums, low, mid, high);
        return count;
    }

    public static int reversePairs(int[] nums) {
        return mergeSort(nums, 0, nums.length - 1);
    }

    public static void main(String[] args) {
        int[] arr = { 1, 3, 2, 3, 1 };
        System.out.println("Reverse Pairs: " + reversePairs(arr));
    }
}
