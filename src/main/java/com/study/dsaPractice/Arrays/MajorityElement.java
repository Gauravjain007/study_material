package com.study.dsaPractice.Arrays;

/**
 * Problem: https://leetcode.com/problems/majority-element/
 * 
 * <p>
 * Approach: Boyer-Moore Voting Algorithm
 * <p>
 * Time Complexity: O(n)
 * <p>
 * Space Complexity: O(1)
 * 
 * <p>
 * Explanation:
 * <ul>
 * <li>The algorithm maintains a count and a candidate for the majority element.
 * <li>It iterates through the array, updating the count and candidate based on
 * the current element.
 * <li>If the count drops to zero, it selects a new candidate.
 * <li>At the end of the iteration, the candidate is returned as the majority
 * element.
 * </ul>
 * The algorithm works on the basis of the assumption that the majority element
 * occurs more than n/2 times in the array. This assumption guarantees that even
 * if the count is reset to 0 by other elements, the majority element will
 * eventually regain the lead.
 */
public class MajorityElement {
    public static int majorityElement(int[] nums) {
        int count = 0;
        int candidate = -1;

        for (int num : nums) {
            if (count == 0) {
                candidate = num;
            }
            if (candidate == num) {
                count++;
            } else {
                count--;
            }
        }

        return candidate;
    }

    public static void main(String[] args) {
        int[] nums = { 3, 2, 3, 2, 2, 1, -1, 2, 2 };
        System.out.println("Majority Element: " + majorityElement(nums));
    }
}
