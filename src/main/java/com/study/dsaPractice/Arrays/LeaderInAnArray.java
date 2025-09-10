package com.study.dsaPractice.Arrays;

import java.util.ArrayList;
import java.util.List;

/**
 * Problem: https://www.geeksforgeeks.org/dsa/leaders-in-an-array/
 * Given an array arr[] of size n, the task is to find all the Leaders in the
 * array. An element is a Leader if it is greater than or equal to all the
 * elements to its right side.
 */
public class LeaderInAnArray {

    public static List<Integer> findLeaders(int[] arr) {
        List<Integer> leaders = new ArrayList<>();
        int n = arr.length - 1;
        int largest = Integer.MIN_VALUE;
        for (int i = n; i >= 0; i--) {
            if (arr[i] > largest) {
                leaders.add(arr[i]);
                largest = arr[i];
            }
        }
        return leaders;
    }

    public static void main(String[] args) {
        int[] arr = { 16, 17, 4, 3, 5, 2 };
        System.out.println("Leaders in the array: " + findLeaders(arr));
    }
}