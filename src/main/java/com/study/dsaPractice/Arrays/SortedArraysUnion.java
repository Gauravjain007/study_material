package com.study.dsaPractice.Arrays;

import java.util.ArrayList;
import java.util.List;

public class SortedArraysUnion {

    public static List<Integer> arrayUnion(int[] arr1, int[] arr2) {

        List<Integer> union = new ArrayList<>();
        int m = arr1.length;
        int n = arr2.length;
        int i = 0;
        int j = 0;

        while (i < m && j < n) {
            if (arr1[i] <= arr2[j]) {
                if (!isValuePresent(union, arr1[i]))
                    union.add(arr1[i]);
                i++;
            } else {
                if (!isValuePresent(union, arr2[j]))
                    union.add(arr2[j]);
                j++;
            }
        }
        if (i == m) {
            fillArray(arr2, union, j);
        } else {
            fillArray(arr1, union, i);
        }
        return union;
    }

    public static void fillArray(int[] arr, List<Integer> union, int startIndex) {
        while (startIndex < arr.length) {
            if (!isValuePresent(union, arr[startIndex]))
                union.add(arr[startIndex]);
            startIndex++;
        }
    }

    public static boolean isValuePresent(List<Integer> union, int val) {
        return !union.isEmpty() && union.get(union.size() - 1) == val;
    }

    public static void main(String[] args) {
        int[] arr1 = { 3, 5, 7, 8, 10, 11 };
        int[] arr2 = { 2, 5, 7, 7, 9, 15, 17 };
        System.out.println(arrayUnion(arr1, arr2));
    }
}
