package com.study.dsaPractice.Arrays;

/**
 * Problem:
 * https://leetcode.com/problems/missing-number/description/
 */
public class FindMissingNumberFromArray {

    public static int findMissingNumber(int[] nums) {
        int n = nums.length + 1;
        int expectedSum = n * (n + 1) / 2;
        int actualSum = 0;

        for (int num : nums) {
            actualSum += num;
        }

        return expectedSum - actualSum;
    }

    public static int findMissingNumberWithXOR(int[] nums) {
        int xor1 = 0;
        int xor2 = 0;

        for (int i = 0; i < nums.length; i++) {
            xor1 ^= nums[i];
            xor2 ^= i + 1;
        }
        xor2 ^= (nums.length + 1); // XOR with the last number n

        return xor1 ^ xor2;
    }

    public static void main(String[] args) {
        int[] nums = { 3, 7, 1, 4, 6, 5 };
        System.out.println(findMissingNumber(nums));
        System.out.println(findMissingNumberWithXOR(nums));
    }
}
