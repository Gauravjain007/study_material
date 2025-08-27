package com.study.dsaPractice.Arrays;

public class CheckIsArraySorted {

    public static boolean checkIsArraySorted(int[] nums) {

        if (nums.length < 2)
            return true;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < nums[i - 1])
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(checkIsArraySorted(new int[] { 1, 10, 10, 100 }));
    }
}
