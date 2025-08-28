package com.study.sorting;

import java.util.Arrays;
import java.util.List;

/**
 * Bubble Sort is one of the simplest sorting algorithms that works by
 * repeatedly stepping through the list, comparing adjacent elements and
 * swapping them if they are in the wrong order. The pass through the list is
 * repeated until the list is sorted.
 * 
 * <pre>
 *  Time Complexity:
 *      Best Case: O(n) - Already sorted array (with optimization)
 *      Average Case: O(n²) - Random order
 *      Worst Case: O(n²) - Reverse sorted array
 * Space Complexity:
 *      Space: O(1) - In-place sorting algorithm
 *      Recursive version: O(n) - Due to recursion stack
 * Detailed Analysis:
 *      Comparisons (worst case): n(n-1)/2 = O(n²)
 *      Swaps (worst case): n(n-1)/2 = O(n²)
 *      Passes (worst case): n-1 passes
 *      Passes (best case with optimization): 1 pass
 * </pre>
 * <p>
 * Algorithm: Bubble Sort
 * <ul>
 * <li>Compare each pair of adjacent elements in the array
 * <li>If they are in wrong order (left > right for ascending), swap them
 * <li>Continue through the entire array
 * <li>After each pass, the largest element "bubbles up" to its correct position
 * <li>Repeat until no more swaps are needed
 * </ul>
 */
public class BubbleSort {

    private static final String ILLEGAL_ARGUMENT_MSG = "Found Empty list! Cannot apply sorting.";

    /**
     * Applies bubble sort to the given int array in ascending order.
     *
     * @param unorderedList the array to be sorted.
     */
    public static void applyBubbleSort(int[] unorderedList) {
        if (unorderedList == null || unorderedList.length == 0) {
            System.out.println(ILLEGAL_ARGUMENT_MSG);
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_MSG);
        }

        int n = unorderedList.length;
        for (int i = 0; i < n - 1; i++) {
            boolean isSwapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (unorderedList[j + 1] < unorderedList[j]) {
                    swap(unorderedList, j, j + 1);
                    isSwapped = true;
                }
            }
            if (!isSwapped)
                break;
        }
    }

    /**
     * Applies bubble sort to the given array in ascending order.
     *
     * @param arr the array to be sorted.
     */
    public static <T extends Comparable<T>> void applyBubbleSort(T[] arr) {
        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;

            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j].compareTo(arr[j + 1]) > 0) {
                    T temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }

            if (!swapped)
                break;
        }
    }

    /**
     * Applies bubble sort to the given Collection List in ascending order. If
     * isreversed is set to true, the list is sorted in descending order.
     *
     * @param unorderedList the list to be sorted.
     * @param isreversed    whether to sort in descending order.
     */
    public static <T extends Comparable<T>> void applyBubbleSort(List<T> unorderedList, boolean isreversed) {
        if (unorderedList == null || unorderedList.isEmpty()) {
            System.out.println(ILLEGAL_ARGUMENT_MSG);
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_MSG);
        }

        int n = unorderedList.size();
        for (int i = n - 1; i > 0; i--) {
            boolean isSwapped = false;
            for (int j = 0; j < i; j++) {
                if ((isreversed ? unorderedList.get(j).compareTo(unorderedList.get(j + 1)) < 0
                        : unorderedList.get(j + 1).compareTo(unorderedList.get(j)) < 0)) {
                    T tmp = unorderedList.get(j);
                    unorderedList.set(j, unorderedList.get(j + 1));
                    unorderedList.set(j + 1, tmp);
                    isSwapped = true;
                }
            }
            if (!isSwapped)
                break;
        }
    }

    /**
     * * Variation of bubble sort that reduces the range of comparison.
     * Applies bubble sort to the given int array in ascending order, using a
     * range-optimized version of the algorithm.
     *
     * @param arr the array to be sorted.
     */
    public static void rangeOptimizedBubbleSort(int[] arr) {
        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            int newEnd = 0;

            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                    swapped = true;
                    newEnd = j; // Last position where swap occurred
                }
            }

            if (!swapped)
                break;

            // Optimize: next pass only needs to go up to newEnd
            n = newEnd + 1;
        }
    }

    /**
     * Helper method to swap two elements in array
     */
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int[] arr = { 10, 3, 7, 2, 8, 7 };
        applyBubbleSort(arr);
        System.out.println(Arrays.toString(arr));

        List<Integer> list = Arrays.asList(10, 3, 7, 2, 8, 7);
        applyBubbleSort(list, true);
        System.out.println(list);
    }
}
