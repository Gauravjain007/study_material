package com.study.dsaPractice.Arrays;

public class FindSecondLargestElement {

    public static int secondLargestElement(int[] nums) {
        int largest = Integer.MIN_VALUE;
        int secondLargest = Integer.MIN_VALUE;

        for (int n : nums) {
            if (n > largest) {
                secondLargest = largest;
                largest = n;
            } else if (n < largest && n > secondLargest) {
                secondLargest = n;
            }
        }
        return (secondLargest == Integer.MIN_VALUE) ? -1 : secondLargest;
    }

    public static void main(String[] args) {
        System.out.println(secondLargestElement(new int[] { 10, 5, 8, 12, 15, 6, 20, 3 }));
    }
}
