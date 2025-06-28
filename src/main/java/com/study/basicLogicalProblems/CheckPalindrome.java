package com.study.basicLogicalProblems;

public class CheckPalindrome {

    /**
     * Checks if the given integer is a palindrome.
     * Using simple iterative approach to reverse the number
     * and compare it with the original number.
     * 
     * @param num the number to check
     * @return true if the number is a palindrome, false otherwise
     */
    public static Boolean checkPalindrome(int num) {
        int n = num;
        int reversed = 0;
        while (n > 0) {
            int digit = n % 10;
            reversed = reversed * 10 + digit;
            n /= 10;
        }

        return reversed == num;
    }

    /**
     * Checks if the given String is a palindrome.
     * Using simple iterative approach to compare from start and end of the string
     * and check if the characters are same from both ends.
     * 
     * @param str the string to check
     * @return true if the string is a palindrome, false otherwise
     */
    public static Boolean checkPalindrome(String str) {
        int len = str.length();
        for (int i = 0; i < len; i++) {
            if (str.charAt(i) != str.charAt(len - i - 1))
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        int num1 = 14513;
        int num2 = 14141;
        String str1 = "gaurav";
        String str2 = "srttrs";
        String str3 = "c$951159$c";
        String isPalindromeStr = "Is palindrome: ";
        System.out.println(isPalindromeStr + checkPalindrome(num1));
        System.out.println(isPalindromeStr + checkPalindrome(num2));
        System.out.println(isPalindromeStr + checkPalindrome(str1));
        System.out.println(isPalindromeStr + checkPalindrome(str2));
        System.out.println(isPalindromeStr + checkPalindrome(str3));
    }
}
