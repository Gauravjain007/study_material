package com.study.sorting;

import java.util.List;
import java.util.Arrays;

/**
 * Selection Sort is one of the simplest sorting algorithms that works by
 * repeatedly finding the minimum element from the unsorted portion of the array
 * and placing it at the beginning.
 * 
 * <pre>
 *  Time Complexity:
 *      Best Case: O(n²) - Even if array is already sorted
 *      Average Case: O(n²) - Random order
 *      Worst Case: O(n²) - Reverse sorted array
 * Space Complexity:
 *      Space: O(1) - In-place sorting algorithm
 * Detailed Analysis:
 *      Comparisons: Always n(n-1)/2 = O(n²)
 *      Swaps: Best case 0, worst case n-1 = O(n)
 *      Memory: Only uses constant extra space for variables
 * </pre>
 * <p>
 * Algorithm: Selection Sort
 * <ul>
 * <li>Find the minimum element in the unsorted portion of the array
 * <li>Swap it with the first element of the unsorted portion
 * <li>Move the boundary between sorted and unsorted portions one position to
 * the right
 * <li>Repeat until the entire array is sorted
 * </ul>
 */
public class SelectionSort {

    private static final String ILLEGAL_ARGUMENT_MSG = "Found Empty list! Cannot apply sorting.";

    /**
     * Applies selection sort to the given list in ascending order without modifying
     * the original list.
     *
     * @param unorderedList the list to be sorted.
     */
    public static <T extends Comparable<T>> void applySelectionSort(T[] unorderedList) {
        applySelectionSort(unorderedList, false);
    }

    /**
     * Applies selection sort to the given Collection List in ascending order.
     *
     * @param unorderedList the Collection List to be sorted.
     */
    public static <T extends Comparable<T>> void applySelectionSort(List<T> unorderedList) {
        applySelectionSort(unorderedList, false);
    }

    /**
     * Applies selection sort to the given list in ascending order. If isreversed is
     * set to true, the list is sorted in descending order.
     *
     * @param unorderedList the list to be sorted.
     * @param isreversed    whether to sort in descending order.
     */
    public static <T extends Comparable<T>> void applySelectionSort(T[] unorderedList, boolean isreversed) {
        if (unorderedList == null || unorderedList.length == 0) {
            System.out.println(ILLEGAL_ARGUMENT_MSG);
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_MSG);
        }

        for (int i = 0; i < unorderedList.length - 1; i++) {
            for (int j = i + 1; j < unorderedList.length; j++) {
                if ((isreversed ? unorderedList[i].compareTo(unorderedList[j]) < 0
                        : unorderedList[j].compareTo(unorderedList[i]) < 0)) {
                    T tmp = unorderedList[i];
                    unorderedList[i] = unorderedList[j];
                    unorderedList[j] = tmp;
                }
            }
        }
    }

    /**
     * Applies selection sort to the given Collection List in ascending order. If
     * isreversed is set to true, the list is sorted in descending order.
     *
     * @param unorderedList the list to be sorted.
     * @param isreversed    whether to sort in descending order.
     */
    public static <T extends Comparable<T>> void applySelectionSort(List<T> unorderedList, boolean isreversed) {
        if (unorderedList == null || unorderedList.isEmpty()) {
            System.out.println(ILLEGAL_ARGUMENT_MSG);
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_MSG);
        }

        for (int i = 0; i < unorderedList.size() - 1; i++) {
            for (int j = i + 1; j < unorderedList.size(); j++) {
                if ((isreversed ? unorderedList.get(i).compareTo(unorderedList.get(j)) < 0
                        : unorderedList.get(j).compareTo(unorderedList.get(i)) < 0)) {
                    T tmp = unorderedList.get(i);
                    unorderedList.set(i, unorderedList.get(j));
                    unorderedList.set(j, tmp);
                }
            }
        }
    }

    /**
     * Applies selection sort to the given int array in ascending order.
     *
     * @param unorderedList the array to be sorted.
     */
    public static void applySelectionSort(int[] unorderedList) {
        if (unorderedList == null || unorderedList.length == 0) {
            System.out.println(ILLEGAL_ARGUMENT_MSG);
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_MSG);
        }

        for (int i = 0; i < unorderedList.length - 1; i++) {
            for (int j = i + 1; j < unorderedList.length; j++) {
                if (unorderedList[j] < unorderedList[i]) {
                    swap(unorderedList, i, j);
                }
            }
        }
    }

    /**
     * * Variation of selection sort that sorts from both ends.
     * Applies bidirectional selection sort to the given int array in ascending
     * order.
     *
     * Bidirectional selection sort is a variation of selection sort that sorts the
     * array from both ends to the middle. This is more efficient than regular
     * selection sort when the array is mostly sorted, since it can skip
     * unnecessary comparisons.
     *
     * @param arr the array to be sorted.
     */
    public static void bidirectionalSelectionSort(int[] arr) {
        int left = 0;
        int right = arr.length - 1;

        while (left < right) {
            int minIndex = left;
            int maxIndex = left;

            // Find both min and max in one pass
            for (int i = left; i <= right; i++) {
                if (arr[i] < arr[minIndex])
                    minIndex = i;
                if (arr[i] > arr[maxIndex])
                    maxIndex = i;
            }

            // Place min at left boundary
            swap(arr, left, minIndex);

            // If max was at left position, it's now at minIndex
            if (maxIndex == left)
                maxIndex = minIndex;

            // Place max at right boundary
            swap(arr, right, maxIndex);

            left++;
            right--;
        }
    }

    /**
     * * Variation of selection sort that is stable.
     * Applies selection sort to the given int array in ascending order, using a
     * stable version of the algorithm that maintains relative order of equal
     * elements.
     *
     * @param arr the array to be sorted.
     */
    public static void stableSelectionSort(int[] arr) {
        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;

            // Find minimum
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }

            // Shift elements to make room and maintain stability
            int minValue = arr[minIndex];
            while (minIndex > i) {
                arr[minIndex] = arr[minIndex - 1];
                minIndex--;
            }
            arr[i] = minValue;
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
        applySelectionSort(arr);
        System.out.println(Arrays.toString(arr));

        String[] strArr = { "Apple", "Orange", "Banana", "Grapes" };
        applySelectionSort(strArr);
        System.out.println(Arrays.toString(strArr));

        List<String> strList = Arrays.asList("Apple", "Orange", "Banana", "Grapes");
        applySelectionSort(strList);
        System.out.println(strList);
        applySelectionSort(strList, true);
        System.out.println(strList);
    }
}
