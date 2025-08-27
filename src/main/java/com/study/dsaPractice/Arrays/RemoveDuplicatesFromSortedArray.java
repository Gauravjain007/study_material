package com.study.dsaPractice.Arrays;

public class RemoveDuplicatesFromSortedArray {

    public static int removeDuplicatesInPlace(int[] nums) {

        if (nums.length < 2)
            return nums.length;

        int i = 0;
        for (int j = 1; j < nums.length; j++) {
            if (nums[i] != nums[j]) {
                i++;
                nums[i] = nums[j];
            }
        }

        return i + 1;
    }

    public static void main(String[] args) {
        System.out.println(removeDuplicatesInPlace(new int[] { 1, 1, 2, 5, 5, 5 }));
    }
}
