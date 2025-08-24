package com.study.sorting;

import java.util.List;
import java.util.Arrays;

/**
 * QuickSort is a highly efficient divide-and-conquer sorting algorithm that
 * works by selecting a 'pivot' element and partitioning the array around this
 * pivot. Elements smaller than the pivot are moved to its left, and elements
 * greater than the pivot are moved to its right.
 * 
 * <pre>
 * Complexity Analysis
 * Time Complexity:
 *      Best & Average Case: O(n log n)
 *      Worst Case: O(n²) - Occurs when pivot is always smallest or largest element. Happens with already sorted or reverse sorted arrays
 * Space Complexity:
 *      Best/Average Case: O(log n) - recursion stack depth
 *      Worst Case: O(n) - when recursion depth equals array size
 * </pre>
 * <p>
 * Algorithm - QuickSort
 * <ul>
 * <li>If the array has one or zero elements, it's already sorted (base case)
 * <li>Choose a pivot element (first, last, middle, or random)
 * <li>Partition the array around the pivot
 * <li>Recursively apply QuickSort to the left sub-array (elements < pivot)
 * <li>Recursively apply QuickSort to the right sub-array (elements > pivot)
 * </ul>
 * Partitioning Procedure - Hoare Partition Scheme:
 * <ul>
 * <li>Choose a pivot element
 * <li>Use two pointers: one from the start, one from the end
 * <li>Move left pointer right until finding element ≥ pivot
 * <li>Move right pointer left until finding element ≤ pivot
 * <li>Swap elements at pointers if they haven't crossed
 * <li>Repeat until pointers cross
 * <li>Place pivot in its correct position
 * </ul>
 */
public class QuickSort {
    private QuickSort() {
        throw new IllegalStateException("Utility class");
    }

    private static final String ILLEGAL_ARGUMENT_MSG = "Found Empty list! Cannot apply sorting.";

    /**
     * Applies QuickSort to the given list in ascending order using Hoare
     * Partition Scheme.
     * 
     * @param unorderedList the list to be sorted.
     */
    public static <T extends Comparable<T>> void hoareQuickSort(List<T> unorderedList) {
        if (unorderedList == null || unorderedList.isEmpty()) {
            System.out.println(ILLEGAL_ARGUMENT_MSG);
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_MSG);
        }

        hoareQuickSort(unorderedList, 0, unorderedList.size() - 1);
    }

    /**
     * Recursively applies QuickSort to the given list in ascending order using
     * Hoare
     * Partition Scheme.
     * 
     * @param unorderedList the list to be sorted.
     * @param low           the starting index of the subarray to be sorted.
     * @param high          the ending index of the subarray to be sorted.
     */
    private static <T extends Comparable<T>> void hoareQuickSort(List<T> unorderedList, int low, int high) {
        if (low >= high)
            return;
        int partitionIdx = hoarePartition(unorderedList, low, high);
        hoareQuickSort(unorderedList, low, partitionIdx);
        hoareQuickSort(unorderedList, partitionIdx + 1, high);
    }

    /**
     * Hoare partition scheme.
     * More efficient than Lomuto as it does fewer swaps on average.
     *
     * @param list the list to be partitioned
     * @param low  the starting index of the subarray to be partitioned
     * @param high the ending index of the subarray to be partitioned
     * @return The index of the pivot after partitioning
     */
    private static <T extends Comparable<T>> int hoarePartition(List<T> list, int low, int high) {
        // Choose first element as pivot
        T pivot = list.get(low);
        int i = low - 1;
        int j = high + 1;
        while (true) {
            // Find element on left that should be on right
            do {
                i++;
            } while (list.get(i).compareTo(pivot) < 0);

            // Find element on right that should be on left
            do {
                j--;
            } while (list.get(j).compareTo(pivot) > 0);

            // If elements crossed, partitioning is done
            if (i >= j)
                return j;

            // Swap elements
            T temp = list.get(i);
            list.set(i, list.get(j));
            list.set(j, temp);
        }
    }

    /**
     * Recursively applies QuickSort to the given list in ascending order using
     * Lomuto
     * Partition Scheme.
     * 
     * @param unorderedList the list to be sorted.
     */
    public static <T extends Comparable<T>> void lomutoQuickSort(List<T> unorderedList) {
        if (unorderedList == null || unorderedList.isEmpty()) {
            System.out.println(ILLEGAL_ARGUMENT_MSG);
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_MSG);
        }

        lomutoQuickSort(unorderedList, 0, unorderedList.size() - 1);
    }

    /**
     * Recursively applies QuickSort to the given list in ascending order using
     * Lomuto
     * Partition Scheme.
     *
     * @param unorderedList the list to be sorted.
     * @param low           the starting index of the subarray to be sorted.
     * @param high          the ending index of the subarray to be sorted.
     */
    private static <T extends Comparable<T>> void lomutoQuickSort(List<T> unorderedList, int low, int high) {
        if (low >= high)
            return;
        int partitionIdx = lomutoPartition(unorderedList, low, high);
        lomutoQuickSort(unorderedList, low, partitionIdx - 1);
        lomutoQuickSort(unorderedList, partitionIdx + 1, high);
    }

    /**
     * Lomuto partition scheme.
     * Takes last element as pivot, places it at correct position,
     * and places all smaller elements to left, greater to right.
     * 
     * @param unorderedList Array to partition
     * @param low           Starting index
     * @param high          Ending index
     * @return Index of pivot after partitioning
     */
    private static <T extends Comparable<T>> int lomutoPartition(List<T> unorderedList, int low, int high) {
        // Choose rightmost element as pivot
        T pivot = unorderedList.get(high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            // If current element is smaller than or equal to pivot
            if (unorderedList.get(j).compareTo(pivot) <= 0) {
                i++;
                T temp = unorderedList.get(i);
                unorderedList.set(i, unorderedList.get(j));
                unorderedList.set(j, temp);
            }
        }

        // Place pivot in correct position
        T temp = unorderedList.get(i + 1);
        unorderedList.set(i + 1, unorderedList.get(high));
        unorderedList.set(high, temp);
        return i + 1;
    }

    public static void main(String[] args) {
        List<String> arr = Arrays.asList("Apple", "Orange", "Banana", "Grapes");
        System.out.println("Unsorted List: " + arr);
        lomutoQuickSort(arr);
        System.out.println("Sorted List: " + arr);

        List<String> arr2 = Arrays.asList("Apple", "Orange", "Banana", "Grapes");
        System.out.println("Unsorted List: " + arr2);
        hoareQuickSort(arr2);
        System.out.println("Sorted List: " + arr2);
    }
}
