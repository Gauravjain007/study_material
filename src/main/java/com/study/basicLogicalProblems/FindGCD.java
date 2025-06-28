package com.study.basicLogicalProblems;

public class FindGCD {
    /**
     * Finds the greatest common divisor of two given numbers
     * using an iterative approach.
     *
     * @param num1 the first number
     * @param num2 the second number
     * @return the greatest common divisor
     */
    public static int findGCD(int num1, int num2) {
        for (int i = Math.min(num1, num2); i > 0; i--) {
            if (num1 % i == 0 && num2 % i == 0) {
                return i;
            }
        }
        return 1;
    }

    /**
     * Finds the greatest common divisor of two given numbers
     * using an recursive approach with the Euclidean algorithm.
     *
     * @param num1 the first number
     * @param num2 the second number
     * @return the greatest common divisor
     */
    public static int optimizedFindGCD(int num1, int num2) {
        return num2 == 0 ? num1 : optimizedFindGCD(num2, num1 % num2);
    }

    public static void main(String[] args) {
        String gcdStr = "GCD: ";
        System.out.println(gcdStr + findGCD(18, 6));
        System.out.println(gcdStr + findGCD(12, 37));
        System.out.println(gcdStr + optimizedFindGCD(39, 27));
        System.out.println(gcdStr + optimizedFindGCD(20, 30));
    }
}
