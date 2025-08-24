package com.study.sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Merge Sort is a divide-and-conquer sorting algorithm that efficiently sorts
 * arrays by recursively dividing them into smaller subarrays, sorting those
 * subarrays, and then merging them back together in sorted order.
 * 
 * <pre>
 * Algorithm Characteristics:
 *      Time Complexity: O(n log n) in all cases (best, average, worst)
 *      Space Complexity: O(n) - requires additional memory for temporary arrays
 *      Stability: Stable - maintains relative order of equal elements
 *      Method: Divide and conquer
 *      Adaptive: No - performance does not improve on partially sorted data
 * Advantages:
 *      Consistent Performance: Always O(n log n) regardless of input
 *      Stable: Maintains relative order of equal elements
 *      Parallelizable: Subproblems can be solved independently
 *      External Sorting: Works well for sorting large datasets that do not fit in memory
 * Disadvantages:
 *      Space Requirements: Needs O(n) additional memory
 *      Not In-place: Standard implementation requires extra space
 *      Not Adaptive: Does not improve on partially sorted data
 *      Overhead: Recursive calls add overhead for small arrays
 * </pre>
 * <p>
 * Algorithm - Merge Sort
 * <ul>
 * <li>If the array has one or zero elements, it's already sorted (base case)
 * <li>Divide the array into two halves at the midpoint
 * <li>Recursively apply merge sort to the left half and then to the right half
 * <li>Merge the two sorted halves back into a single sorted array
 * </ul>
 * Variations:
 * <ul>
 * <li><b>Natural Merge Sort:</b> Identifies existing runs of sorted data and
 * merges them, making it adaptive to partially sorted input.
 * <li><b>Bottom-up Merge Sort:</b> Iterative implementation that starts with
 * small subarrays and progressively merges larger ones.
 * <li><b>Multi-way Merge Sort:</b> Divides the array into k parts instead of
 * just 2, useful for external sorting.
 * </ul>
 */
public class MergeSort {

    private MergeSort() {
        throw new IllegalStateException("Utility class");
    }

    private static final String ILLEGAL_ARGUMENT_MSG = "Found Empty list! Cannot apply sorting.";

    /**
     * Applies merge sort to the given list in ascending order.
     * 
     * @param unorderedList the list to be sorted.
     */
    public static <T extends Comparable<T>> void mergeSort(List<T> unorderedList) {
        if (unorderedList == null || unorderedList.isEmpty()) {
            System.out.println(ILLEGAL_ARGUMENT_MSG);
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_MSG);
        }

        mergeSort(unorderedList, 0, unorderedList.size() - 1);
    }

    /**
     * Recursively applies merge sort to the given list in ascending order.
     * 
     * @param unorderedList the list to be sorted.
     * @param low           the starting index of the list.
     * @param high          the ending index of the list.
     */
    public static <T extends Comparable<T>> void mergeSort(List<T> unorderedList, int low, int high) {
        if (low >= high)
            return;
        int mid = low + (high - low) / 2;
        mergeSort(unorderedList, low, mid);
        mergeSort(unorderedList, mid + 1, high);
        merge(unorderedList, low, mid, high);
    }

    /**
     * Merges two sorted sublists into a single sorted list.
     * 
     * @param unorderedList the list that contains the two sorted sublists.
     * @param low           the starting index of the first sublist.
     * @param mid           the ending index of the first sublist.
     * @param high          the ending index of the second sublist.
     */
    private static <T extends Comparable<T>> void merge(List<T> unorderedList, int low, int mid, int high) {
        List<T> tempList = new ArrayList<>();
        int left = low;
        int right = mid + 1;

        while (left <= mid && right <= high) {
            if (unorderedList.get(right).compareTo(unorderedList.get(left)) < 0) {
                tempList.add(unorderedList.get(right++));
            } else {
                tempList.add(unorderedList.get(left++));
            }
        }

        while (left <= mid) {
            tempList.add(unorderedList.get(left++));
        }

        while (right <= high) {
            tempList.add(unorderedList.get(right++));
        }

        for (int i = low; i <= high; i++) {
            unorderedList.set(i, tempList.get(i - low));
        }
    }

    public static void main(String[] args) {
        List<String> strArr = Arrays.asList("Apple", "Orange", "Banana", "Grapes");
        mergeSort(strArr);
        System.out.println("Sorted List: " + strArr);

        List<Integer> arr = Arrays.asList(10, 7, 8, 9, 1, 5);
        mergeSort(arr);
        System.out.println("Sorted List: " + arr);
    }
}
