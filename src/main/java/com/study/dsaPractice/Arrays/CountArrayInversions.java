package com.study.dsaPractice.Arrays;

/**
 * Problem:
 * https://takeuforward.org/data-structure/count-inversions-in-an-array/
 */
public class CountArrayInversions {

    /**
     * Merges two sorted halves of an array and count the number of inversions.
     * 
     * @param arr  the array to merge
     * @param low  the starting index of the first half
     * @param mid  the ending index of the first half
     * @param high the ending index of the second half
     * @return the number of inversions in the array
     */
    private static int merge(int[] arr, int low, int mid, int high) {
        int count = 0;
        int left = low;
        int right = mid + 1;
        int[] tmp = new int[high - low + 1];
        int i = 0;

        while (left <= mid && right <= high) {
            if (arr[left] > arr[right]) {
                /**
                 * If arr[left] > arr[right], then all remaining elements in left subarray
                 * (arr[left], arr[left+1], ..., arr[mid]) are greater than arr[right]
                 */
                count += (mid - left + 1);
                tmp[i++] = arr[right++];
            } else {
                tmp[i++] = arr[left++];
            }
        }

        while (left <= mid) {
            tmp[i++] = arr[left++];
        }

        while (right <= high)
            tmp[i++] = arr[right++];

        for (int j = 0; j < tmp.length; j++) {
            arr[low + j] = tmp[j];
        }

        return count;
    }

    /**
     * Recursively sorts an array and counts the number of inversions.
     *
     * @param arr  the array to sort
     * @param low  the starting index of the subarray to sort
     * @param high the ending index of the subarray to sort
     * @return the number of inversions in the array
     */
    private static int mergeSort(int[] arr, int low, int high) {
        if (low >= high)
            return 0;

        int count = 0;
        int mid = low + (high - low) / 2;

        count += mergeSort(arr, low, mid);
        count += mergeSort(arr, mid + 1, high);
        count += merge(arr, low, mid, high);

        return count;
    }

    /**
     * Counts the number of inversions in a given array using merge sort approach.
     * Complexity: O(n log n) time and O(n) space.
     * 
     * @param arr the array to count inversions in
     * @return the number of inversions in the array
     */
    public static int countArrayInversions(int[] arr) {
        return mergeSort(arr, 0, arr.length - 1);
    }

    public static void main(String[] args) {
        int[] arr = { 5, 3, 2, 1, 4 };
        System.out.println("Inversion Count: " + countArrayInversions(arr));
    }
}
