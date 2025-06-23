package com.study.custom_collections;

import java.util.ArrayList;
import java.util.List;

import com.study.exception_handling.MyException;

public class CustomHeap<T extends Comparable<T>> {
    private List<T> heap = new ArrayList<>();
    private boolean isMinHeap = true;

    public CustomHeap() {
    }

    public CustomHeap(boolean isMaxHeap) {
        this.isMinHeap = !isMaxHeap;
    }

    /**
     * Finds the parent index of a given index in the heap.
     *
     * @param index index for which the parent index is to be found
     * @return parent index
     */
    private int parent(int index) {
        return (index - 1) / 2;
    }

    /**
     * Finds the left child index of a given index in the heap.
     *
     * @param index index for which the left child index is to be found
     * @return left child index
     */
    private int left(int index) {
        return (index * 2) + 1;
    }

    /**
     * Finds the right child index of a given index in the heap.
     *
     * @param index index for which the right child index is to be found
     * @return right child index
     */
    private int right(int index) {
        return (index * 2) + 2;
    }

    /**
     * Swaps the elements at the given indices in the heap.
     * 
     * @param idx1 index of the first element to be swapped
     * @param idx2 index of the second element to be swapped
     */
    private void swap(int idx1, int idx2) {
        T temp = this.heap.get(idx1);
        this.heap.set(idx1, this.heap.get(idx2));
        this.heap.set(idx2, temp);
    }

    /**
     * Moves the element at the given index up the heap until it is in the correct
     * position.
     * 
     * @param index index of the element to be moved up the heap
     */
    private void upHeap(int index) {
        if (index == 0)
            return;
        int parent = parent(index);
        if (isMinHeap) {
            if (this.heap.get(parent).compareTo(this.heap.get(index)) > 0) {
                swap(parent, index);
                upHeap(parent);
            }
        } else {
            if (this.heap.get(parent).compareTo(this.heap.get(index)) < 0) {
                swap(parent, index);
                upHeap(parent);
            }
        }
    }

    /**
     * Moves the element at the given index down the heap until it is in the correct
     * position.
     * 
     * @param index index of the element to be moved down the heap
     */
    private void downHeap(int index) {
        int left = left(index);
        int right = right(index);
        int minIndex = index;

        if (isMinHeap) {
            if (left < heap.size() && heap.get(left).compareTo(heap.get(minIndex)) < 0) {
                minIndex = left;
            }
            if (right < heap.size() && heap.get(right).compareTo(heap.get(minIndex)) < 0) {
                minIndex = right;
            }
        } else {
            if (left < heap.size() && heap.get(left).compareTo(heap.get(minIndex)) > 0) {
                minIndex = left;
            }
            if (right < heap.size() && heap.get(right).compareTo(heap.get(minIndex)) > 0) {
                minIndex = right;
            }
        }

        if (minIndex != index) {
            swap(minIndex, index);
            downHeap(minIndex);
        }
    }

    /**
     * Inserts the given value into the heap and reorders the heap to maintain
     * the heap property. The heap property is maintained by moving the inserted
     * element up the heap until it is in the correct position.
     *
     * @param value the value to be inserted into the heap
     */
    public void insert(T value) throws MyException {
        if (value == null) {
            throw new MyException("Heap does not allow null values");
        }
        heap.add(value);
        upHeap(this.heap.size() - 1);
    }

    /**
     * Removes the root element from the heap and reorders the heap to maintain
     * the heap property. The heap property is maintained by moving the last
     * element of the heap to the root and then moving it down the heap until it
     * is in the correct position.
     *
     * @return the value of the removed root element
     * @throws MyException if the heap is empty
     */
    public T remove() throws MyException {
        if (heap.isEmpty())
            throw new MyException("Can't remove element from an empty list");
        T temp = this.heap.get(0);
        if (this.heap.size() == 1) {
            this.heap.remove(0);
        } else {
            this.heap.set(0, this.heap.remove(this.heap.size() - 1));
            downHeap(0);
        }
        return temp;
    }

    /**
     * Returns the root element of the heap without removing it.
     *
     * @return the value of the root element
     * @throws MyException if the heap is empty
     */
    public T peek() throws MyException {
        if (heap.isEmpty())
            throw new MyException("Can't peek element from an empty list");
        return this.heap.get(0);
    }

    /**
     * Returns the underlying list used to store the elements of the heap. Note
     * that this list is not a copy and changes made to it will directly affect
     * the heap.
     *
     * @return the underlying list
     */
    public List<T> getHeap() {
        return heap;
    }

    @Override
    public String toString() {
        return "Heap: " + heap;
    }

    public static void main(String[] args) {
        try {
            CustomHeap<Integer> minHeap = new CustomHeap<>();
            minHeap.insert(5);
            minHeap.insert(3);
            minHeap.insert(8);
            minHeap.insert(1);
            minHeap.insert(4);

            System.out.println("Min Heap: " + minHeap.heap);
            System.out.println("Peeked: " + minHeap.peek());
            System.out.println("Removed: " + minHeap.remove());
            System.out.println("Min Heap after removal: " + minHeap.heap);

            CustomHeap<Integer> maxHeap = new CustomHeap<>(true);
            maxHeap.insert(5);
            maxHeap.insert(3);
            maxHeap.insert(8);
            maxHeap.insert(1);
            maxHeap.insert(4);

            System.out.println("Max Heap: " + maxHeap.heap);
            System.out.println("Peeked: " + maxHeap.peek());
            System.out.println("Removed: " + maxHeap.remove());
            System.out.println("Max Heap after removal: " + maxHeap.heap);

            maxHeap.insert(null); // This will throw MyException
        } catch (MyException e) {
            System.err.println(e.getMessage());
        }
    }
}
