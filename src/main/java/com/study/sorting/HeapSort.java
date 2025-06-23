package com.study.sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.study.custom_collections.CustomHeap;
import com.study.exception_handling.MyException;

public class HeapSort<T extends Comparable<T>> {

    /**
     * Sorts the given list of elements using the Heap Sort algorithm.
     *
     * @param array the list of elements to be sorted
     * @return the sorted list of elements
     * @throws MyException if the given list is empty
     */
    public List<T> heapSort(List<T> array) throws MyException {
        CustomHeap<T> heap = new CustomHeap<>();
        for (T element : array) {
            heap.insert(element);
        }
        List<T> sortedList = new ArrayList<>();
        while (!heap.getHeap().isEmpty()) {
            sortedList.add(heap.remove());
        }
        return sortedList;
    }

    /**
     * Sorts the given array of elements using the Heap Sort algorithm.
     *
     * @param array the array of elements to be sorted
     * @return the sorted list of elements
     * @throws MyException if the given array is empty or if any element is null
     */
    public List<T> heapSort(T[] array) throws MyException {
        CustomHeap<T> heap = new CustomHeap<>();
        for (T element : array) {
            heap.insert(element);
        }
        List<T> sortedList = new ArrayList<>();
        while (!heap.getHeap().isEmpty()) {
            sortedList.add(heap.remove());
        }
        return sortedList;
    }

    public static void main(String[] args) throws MyException {
        HeapSort<String> obj = new HeapSort<>();
        String[] arr = { "date", "banana", "apple", "cherry", "elderberry" };
        System.out.println("Array before sorting: " + Arrays.toString(arr));
        System.out.println("Array after Heap Sort: " + obj.heapSort(arr));
        System.out.println("Array (as List) after Heap Sort: " + obj.heapSort(Arrays.asList(arr)));
    }
}
