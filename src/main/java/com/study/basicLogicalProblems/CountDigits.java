package com.study.basicLogicalProblems;

public class CountDigits {

    /**
     * Counts the number of digits in a given integer.
     *
     * @param num the integer to count digits from
     * @return the number of digits in the given integer
     */
    public static Integer countDigits(int num) {
        int count = 0;
        while (num > 0) {
            num /= 10;
            count++;
        }
        return count;
    }

    /**
     * Counts the number of digits in a given integer using
     * the Math.log10() function, which is more efficient than
     * the iterative approach.
     *
     * @param num the integer to count digits from
     * @return the number of digits in the given integer
     */
    public static Integer optimalCountDigits(int num) {
        return (int) Math.log10(num) + 1;
    }

    public static void main(String[] args) {
        System.out.println("Digit Count: " + countDigits(134261));
        System.out.println("Digit Count: " + optimalCountDigits(134261));
    }
}