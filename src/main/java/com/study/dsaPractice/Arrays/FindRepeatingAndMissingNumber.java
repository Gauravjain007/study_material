package com.study.dsaPractice.Arrays;

/**
 * Problem:
 * https://takeuforward.org/data-structure/find-the-repeating-and-missing-numbers/
 */
public class FindRepeatingAndMissingNumber {

    /**
     * Finds the repeating and missing numbers in the array. Using math.
     * Complexity: O(n) time and O(1) space.
     * 
     * @param arr The input array containing numbers from 1 to n with one number
     *            missing and one number repeating.
     * @return An array where the first element is the repeating number and the
     *         second element is the missing number.
     */
    public static int[] findRepeatingAndMissingNumber(int[] arr) {
        int n = arr.length;

        long sn = (long) n * (n + 1) / 2;
        long s2n = (long) n * (n + 1) * (2 * n + 1) / 6;

        long s = 0L;
        long s2 = 0L;

        for (int num : arr) {
            s += num;
            s2 += num * num;
        }

        // X - Y = S - Sn
        long val1 = s - sn;
        // X^2 - Y^2 = S2 - S2n => X + Y = S2 - S2n / (X - Y)
        long val2 = (s2 - s2n) / val1;

        // (X - Y) + (X + Y) = (S - Sn) + (S2 - S2n)/(S - Sn)
        // 2X = val1 + val2
        long x = (val1 + val2) / 2;
        long y = x - val1;

        return new int[] { (int) x, (int) y };
    }

    /**
     * Finds the repeating and missing numbers in the array. Using frequency array.
     * Complexity: O(n) time and O(n) space.
     * 
     * @param arr The input array containing numbers from 1 to n with one number
     *            missing and one number repeating.
     * @return An array where the first element is the repeating number and the
     *         second element is the missing number.
     */
    public static int[] findRepeatingAndMissingNumber2(int[] arr) {
        int n = arr.length;
        int[] freq = new int[n + 1];
        int repeating = -1;
        int missing = -1;
        for (int num : arr) {
            freq[num]++;
        }
        for (int i = 1; i <= n; i++) {
            if (freq[i] == 2) {
                repeating = i;
            }
            if (freq[i] == 0) {
                missing = i;
            }
            if (repeating != -1 && missing != -1) {
                break;
            }
        }

        return new int[] { repeating, missing };
    }

    public static void main(String[] args) {
        int[] arr = { 3, 1, 2, 5, 3 };
        int[] result = findRepeatingAndMissingNumber(arr);
        System.out.println("Repeating Number: " + result[0]);
        System.out.println("Missing Number: " + result[1]);

        int[] result2 = findRepeatingAndMissingNumber2(arr);
        System.out.println("Repeating Number: " + result2[0]);
        System.out.println("Missing Number: " + result2[1]);
    }
}
