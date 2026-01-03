package com.study.dsaPractice.Strings;

/**
 * Remove Outer Parentheses - LeetCode 1021
 */
public class RemoveOuterParentheses {

    /**
     * Removes the outermost parentheses of a given string, if it exists using a
     * depth counter.
     * <p>
     * Time Complexity: O(n)
     * <p>
     * Space Complexity: O(1)
     * 
     * @param s the given string
     * @return the resulting string after the outermost parentheses are removed
     */
    public static String removeOuterParentheses(String s) {
        StringBuilder sb = new StringBuilder("");
        int depth = 0;
        for (char ch : s.toCharArray()) {
            if (ch == '(') {
                if (depth > 0)
                    sb.append(ch);
                depth++;
            } else {
                depth--;
                if (depth > 0)
                    sb.append(ch);
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String s = "(()())(())";
        System.out.println("Original String: " + s);
        System.out.println("Result: " + removeOuterParentheses(s)); // Output: ()()()

        s = "(()())(())(()(()))";
        System.out.println("Original String: " + s);
        System.out.println("Result: " + removeOuterParentheses(s)); // Output: ()()()()(())
    }
}
