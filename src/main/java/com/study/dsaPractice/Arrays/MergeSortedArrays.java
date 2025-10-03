package com.study.dsaPractice.Arrays;

import java.util.Arrays;

/**
 * Problem: https://leetcode.com/problems/merge-sorted-array/
 */
public class MergeSortedArrays {

    /**
     * Merge two sorted arrays into one sorted array in-place with O(1) extra space.
     * 
     * @param nums1 The first sorted array. [m + n] size, with first m elements
     *              valid.
     * @param m     The number of elements in the first array.
     * @param nums2 The second sorted array.
     * @param n     The number of elements in the second array.
     */
    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1;
        int j = n - 1;
        int k = m + n - 1;
        while (j >= 0) {
            if (i >= 0 && nums1[i] > nums2[j]) {
                nums1[k--] = nums1[i--];
            } else {
                nums1[k--] = nums2[j--];
            }
        }
    }

    public static void main(String[] args) {
        int[] nums1 = { 1, 2, 3, 0, 0, 0 };
        int[] nums2 = { 2, 5, 6 };
        merge(nums1, 3, nums2, 3);
        System.out.println(Arrays.toString(nums1));
    }
}
