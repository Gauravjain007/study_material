package com.study.dsaPractice.Arrays;

public class FindTheSingleOccurenceNumber {
    public static int findTheSingleOccurenceNumber(int[] nums) {
        int xor = 0;
        for (int num : nums) {
            xor ^= num;
        }
        return xor;
    }

    public static void main(String[] args) {
        int[] nums = { 4, 1, 2, 1, 2, 4, 5 };
        System.out.println(findTheSingleOccurenceNumber(nums));
    }
}
