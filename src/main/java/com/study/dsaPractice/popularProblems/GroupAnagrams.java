package com.study.dsaPractice.popularProblems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Problem: https://leetcode.com/problems/group-anagrams/
 */
public class GroupAnagrams {

    /**
     * Given an array of strings, group the anagrams together.
     *
     * The algorithm works by sorting each string and using the sorted string as a
     * key in a hashmap. The value for each key is a list of strings which are
     * anagrams of each other.
     *
     * @param strs an array of strings
     * @return a list of lists of strings, where each sublist contains anagrams of
     *         each other
     */
    public static List<List<String>> groupAnagramsUsingStringSort(String[] strs) {
        Map<String, List<String>> hmap = new HashMap<>();

        for (String str : strs) {
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            String sortedStr = new String(chars);
            hmap.putIfAbsent(sortedStr, new ArrayList<>());
            hmap.get(sortedStr).add(str);
        }

        return new ArrayList<>(hmap.values());
    }

    /**
     * Given an array of strings, group the anagrams together.
     * Uses a frequency array to count the frequency of each character in the
     * string, and then uses this frequency array as a key to group the anagrams
     * together in a HashMap. Finally, returns a list of lists containing the
     * grouped anagrams.
     * 
     * @param strs an array of strings
     * @return a list of lists containing the grouped anagrams
     */
    public static List<List<String>> groupAnagramsUsingFreqArray(String[] strs) {
        Map<String, List<String>> hmap = new HashMap<>();

        for (String str : strs) {
            char[] freq = new char[26];
            for (char ch : str.toCharArray()) {
                freq[ch - 'a']++;
            }
            String key = Arrays.toString(freq);
            hmap.putIfAbsent(key, new ArrayList<>());
            hmap.get(key).add(str);
        }

        return new ArrayList<>(hmap.values());
    }

    public static void main(String[] args) {
        String[] strs = { "eat", "tea", "tan", "ate", "nat", "bat" };
        System.out.println(groupAnagramsUsingStringSort(strs));
        System.out.println(groupAnagramsUsingFreqArray(strs));
    }
}
