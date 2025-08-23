package com.study.sorting;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Insertion Sort is a simple and intuitive sorting algorithm that builds the
 * final sorted array one element at a time. It works similarly to how you might
 * sort playing cards in your hand - you take one card at a time and insert it
 * into its correct position among the already sorted cards. Despite its O(n²)
 * worst-case complexity, it's highly efficient for small datasets and performs
 * exceptionally well on nearly sorted data.
 * 
 * <pre>
 * Time Complexity:
 *      Best Case: O(n) - Already sorted array
 *      Average Case: O(n²) - Random order
 *      Worst Case: O(n²) - Reverse sorted array
 * Space Complexity:
 *     Space: O(1) - In-place sorting algorithm
 * Detailed Analysis:
 *     Comparisons (worst case): n(n-1)/2 = O(n²)
 *     Swaps (worst case): n(n-1)/2 = O(n²)
 *     Passes (worst case): n-1 passes
 *     Passes (best case): 1 pass
 * </pre>
 * <p>
 * Algorithm: Insertion Sort
 * <ul>
 * <li>Start with the second element (the first element is considered sorted)
 * <li>Compare the current element with the elements in the sorted portion
 * <li>Shift larger elements one position to the right
 * <li>Insert the current element into its correct position
 * <li>Repeat until the entire array is sorted
 * </ul>
 * </p>
 * <p>
 * Ideal Use Cases:
 * <ul>
 * <li>Small arrays: Arrays with ≤ 50 elements (often fastest option)
 * <li>Nearly sorted data: Excellent O(n) performance on almost sorted arrays
 * <li>Online sorting: Sorting data as it arrives in real-time
 * <li>Hybrid algorithms: As a subroutine in advanced algorithms (TimSort,
 * Introsort)
 * <li>Stable sorting required: When maintaining relative order is crucial
 * </ul>
 * </p>
 */
public class InsertionSort {

    private InsertionSort() {
        throw new IllegalStateException("Utility class");
    }

    private static final String ILLEGAL_ARGUMENT_MSG = "Found Empty list! Cannot apply sorting.";

    /**
     * Applies insertion sort to the given int array in ascending order.
     * 
     * @param unorderedList the array to be sorted.
     */
    public static void insertionSort(int[] unorderedList) {
        if (unorderedList == null || unorderedList.length == 0) {
            System.out.println(ILLEGAL_ARGUMENT_MSG);
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_MSG);
        }

        int n = unorderedList.length;
        for (int i = 1; i < n; i++) {
            for (int j = i; j > 0; j--) {
                if (unorderedList[j] < unorderedList[j - 1]) {
                    swap(unorderedList, j - 1, j);
                } else {
                    break;
                }
            }
        }
    }

    /**
     * Applies insertion sort to the given array in ascending order, using a
     * provided comparator.
     * 
     * @param arr        the array to be sorted.
     * @param comparator the comparator to compare elements.
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        int n = arr.length;

        for (int i = 1; i < n; i++) {
            T key = arr[i];
            int j = i - 1;

            while (j >= 0 && comparator.compare(arr[j], key) > 0) {
                arr[j + 1] = arr[j];
                j--;
            }

            arr[j + 1] = key;
        }
    }

    /**
     * * Variation of insertion sort that is more efficient for large datasets.
     * Applies binary insertion sort to the given int array in ascending order.
     * Insertion sort optimized with binary search for finding insertion position.
     * Reduces comparisons from O(n) to O(log n) per insertion.
     * 
     * @param arr the array to be sorted.
     */
    public static void binaryInsertionSort(int[] arr) {
        int n = arr.length;

        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int left = 0;
            int right = i;

            // Binary search for correct position
            while (left < right) {
                int mid = left + (right - left) / 2;
                if (arr[mid] > key) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            }

            // Shift elements to make room
            for (int j = i; j > left; j--) {
                arr[j] = arr[j - 1];
            }

            // Insert key at correct position
            arr[left] = key;
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
        insertionSort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
