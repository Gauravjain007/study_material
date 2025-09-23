package com.study.dsaPractice.Arrays;

import java.util.ArrayList;
import java.util.List;

/**
 * Problem: https://leetcode.com/problems/majority-element/
 * 
 * <p>
 * Approach: Boyer-Moore Voting Algorithm
 * <p>
 * Time Complexity: O(n)
 * <p>
 * Space Complexity: O(1)
 */
public class MajorityElement {

    /**
     * <p>
     * Explanation:
     * <ul>
     * <li>The algorithm maintains two counts and two candidates for the majority
     * elements.
     * <li>It iterates through the array, updating the counts and candidates based
     * on
     * the current element.
     * <li>If the count for a candidate drops to zero, it selects a new candidate.
     * <li>At the end of the iteration, the candidates are returned as the majority
     * elements if they occur more than n/3 times in the array.
     * </ul>
     * The algorithm works on the basis of the assumption that the majority elements
     * occur more than n/3 times in the array. This assumption guarantees that even
     * if the count is reset to 0 by other elements, the majority elements will
     * eventually regain the lead.
     */
    public static List<Integer> nBy3MajorityElement(int[] nums) {
        int count1 = 0;
        int count2 = 0;
        int cand1 = -1;
        int cand2 = -1;

        for (int num : nums) {
            if (num == cand1) {
                count1++;
            } else if (num == cand2) {
                count2++;
            } else if (count1 == 0) {
                cand1 = num;
                count1 = 1;
            } else if (count2 == 0) {
                cand2 = num;
                count2 = 1;
            } else {
                count1--;
                count2--;
            }
        }

        count1 = 0;
        count2 = 0;
        for (int num : nums) {
            if (num == cand1)
                count1++;
            else if (num == cand2)
                count2++;
        }

        List<Integer> res = new ArrayList<>();

        if (count1 >= nums.length / 3 + 1)
            res.add(cand1);
        if (count2 >= nums.length / 3 + 1)
            res.add(cand2);

        return res;
    }

    /**
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
     * 
     * @param nums
     * @return
     */
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
