package com.study.dsaPractice.Arrays;

import java.util.HashMap;
import java.util.Map;

/**
 * Problem:
 * https://takeuforward.org/data-structure/count-the-number-of-subarrays-with-given-xor-k/
 */
public class FindSubArrayWithXorSumK {

    /**
     * Finds the count of subarrays with XOR sum equal to k using prefix XOR and a
     * hash map.
     * 
     * @param nums the input array
     * @param k    the target XOR sum
     * @return the count of subarrays with XOR sum equal to k
     */
    public static int findSubArrayCountWithXorK(int[] nums, int k) {
        int count = 0;
        Map<Integer, Integer> xorMap = new HashMap<>();
        int prefixXor = 0;

        for (int num : nums) {
            prefixXor ^= num;

            if (prefixXor == k) {
                count++;
            }

            count += xorMap.getOrDefault(prefixXor ^ k, 0);
            xorMap.put(prefixXor, xorMap.getOrDefault(prefixXor, 0) + 1);
        }

        return count;
    }

    public static void main(String[] args) {
        int[] nums = { 4, 2, 2, 6, 4 };
        int k = 6;
        System.out.println(findSubArrayCountWithXorK(nums, k));
    }
}
