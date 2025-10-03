package com.study.dsaPractice.Arrays;

/**
 * Problem: https://leetcode.com/problems/maximum-product-subarray/
 */
public class MaxProductSubarray {

    /**
     * Finds the maximum product of a contiguous subarray within the given array.
     * 
     * @param nums the input array
     * @return the maximum product of a contiguous subarray
     */
    public static int maxProduct(int[] nums) {
        int n = nums.length;
        int preProd = 1;
        int sufProd = 1;
        int maxProd = nums[0];

        for (int i = 0; i < nums.length; i++) {
            if (preProd == 0)
                preProd = 1;
            if (sufProd == 0)
                sufProd = 1;

            preProd *= nums[i];
            sufProd *= nums[n - i - 1];

            maxProd = Math.max(maxProd, Math.max(preProd, sufProd));
        }

        return maxProd;
    }

    public static void main(String[] args) {
        int[] nums = { 2, 3, -2, 4 };
        System.out.println("Max Product Subarray: " + maxProduct(nums)); // {2,3} = 6
    }
}
