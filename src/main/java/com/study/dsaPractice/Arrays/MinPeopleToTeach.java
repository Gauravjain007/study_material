package com.study.dsaPractice.Arrays;

import java.util.HashSet;
import java.util.Set;

/**
 * Problem: https://leetcode.com/problems/minimum-number-of-people-to-teach/
 */
public class MinPeopleToTeach {

    private static boolean hasCommonLang(int[] arr1, int[] arr2) {
        for (int i : arr1)
            for (int j : arr2)
                if (i == j)
                    return true;
        return false;
    }

    public static int minimumTeachings(int n, int[][] languages, int[][] friendships) {

        // Find friendships without common languages
        Set<Integer> cantComm = new HashSet<>();
        for (int[] friendship : friendships) {
            if (!hasCommonLang(languages[friendship[0] - 1], languages[friendship[1] - 1])) {
                cantComm.add(friendship[0] - 1);
                cantComm.add(friendship[1] - 1);
            }
        }

        // Find max number of speakers of a language among the friendships without
        // common languages
        int max = 0;
        int[] langSpeakers = new int[n + 1];
        for (int friend : cantComm) {
            for (int lang : languages[friend]) {
                langSpeakers[lang]++;
                max = Math.max(langSpeakers[lang], max);
            }
        }

        return cantComm.size() - max;
    }

    public static void main(String[] args) {
        int[][] languages = { { 1 }, { 2 }, { 1, 2 } };
        int[][] friendships = { { 1, 2 }, { 1, 3 }, { 2, 3 } };
        System.out.println(minimumTeachings(2, languages, friendships));
    }
}
