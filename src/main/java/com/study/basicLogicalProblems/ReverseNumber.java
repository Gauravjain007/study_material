package com.study.basicLogicalProblems;

public class ReverseNumber {

    /**
     * Reverses the given number.
     *
     * @param num the number to reverse
     * @return the reversed number
     */
    public static Integer reversedNumber(int num) {
        int reversed = 0;
        while (num > 0) {
            int digit = num % 10;
            reversed = reversed * 10 + digit;
            num /= 10;
        }
        return reversed;
    }

    public static void main(String[] args) {
        System.out.println("Reversed Number: " + reversedNumber(14513));
    }
}
