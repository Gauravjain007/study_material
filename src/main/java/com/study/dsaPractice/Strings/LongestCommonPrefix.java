package com.study.dsaPractice.Strings;

/**
 * Longest Common Prefix - LeetCode 14
 */
public class LongestCommonPrefix {

    /**
     * Returns the longest common prefix (LCP) of an array of strings.
     * The LCP is the empty string if there is no common prefix among the strings.
     * Otherwise, the LCP is the longest string that is a prefix of all the strings.
     * <p>
     * Time Complexity: O(n * m) where n is the number of strings and m is the
     * length of the shortest string.
     * <p>
     * Space Complexity: O(1)
     * 
     * @param strs the array of strings
     * @return the longest common prefix of the strings in the array
     */
    public static String longestCommonPrefix(String[] strs) {
        // first string as prefix
        String prefix = strs[0];
        // compare with each string
        for (int i = 1; i < strs.length; i++) {
            // reduce prefix until it matches the prefix of current string
            while (strs[i].indexOf(prefix) != 0) {
                prefix = prefix.substring(0, prefix.length() - 1);
                if (prefix.isEmpty())
                    return "";
            }
        }
        return prefix;
    }

    public static void main(String[] args) {
        String[] strs = { "flower", "flow", "flight" };
        System.out.println(longestCommonPrefix(strs));
    }
}
