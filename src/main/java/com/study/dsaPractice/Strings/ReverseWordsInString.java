package com.study.dsaPractice.Strings;

/**
 * Reverse Words in a String - LeetCode 151
 */
public class ReverseWordsInString {

    /**
     * (Recommended) Given an input string, reverse the order of words. Use two
     * pointers to
     * traverse the string from the end to the beginning.
     * <p>
     * Time Complexity: O(n)
     * <p>
     * Space Complexity: O(1)
     * 
     * @param s the input string
     * @return the reversed string
     */
    public static String reverseWords(String s) {
        StringBuilder sb = new StringBuilder("");
        int i = s.length() - 1;
        int j = 0;

        while (i >= 0) {
            // skip trailing spaces
            while (i >= 0 && s.charAt(i) == ' ')
                i--;

            j = i;

            // find the start of the word
            while (j >= 0 && s.charAt(j) != ' ')
                j--;

            // append the word
            sb.append(s.substring(j + 1, i + 1)).append(" ");

            i = j;
        }

        // remove the trailing space
        return sb.toString().trim();
    }

    /**
     * (Not Recommended) Alternative approach using built-in functions to split and
     * join the words.
     * <p>
     * Time Complexity: O(n)
     * <p>
     * Space Complexity: O(n)
     * 
     * @param s the input string
     * @return the reversed string
     */
    public static String reverseWords2(String s) {
        StringBuilder sb = new StringBuilder("");

        // trim and split by spaces to convert the string into an array of words
        String[] words = s.trim().split("\\s+");

        // append words in reverse order
        for (int i = words.length - 1; i > 0; i--) {
            sb.append(words[i]).append(" ");
        }
        sb.append(words[0]);

        return sb.toString();
    }

    public static void main(String[] args) {
        String s = "  ocean  is blue  ";
        System.out.println("Original String: \"" + s + "\"");
        System.out.println("Reversed Words 1: \"" + reverseWords(s) + "\""); // Output: "blue is ocean"
        System.out.println("Reversed Words 2: \"" + reverseWords2(s) + "\""); // Output: "blue is ocean"

        s = "    ";
        System.out.println("Original String: \"" + s + "\"");
        System.out.println("Reversed Words 1: \"" + reverseWords(s) + "\""); // Output: ""
    }
}
