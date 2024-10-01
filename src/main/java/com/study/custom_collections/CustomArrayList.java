package com.study.custom_collections;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Implements Custom Arraylist of the Collections Framework
 */
public class CustomArrayList<T> {
    private static final int DEFAULT_SIZE = 10;
    private int size = 0;
    private Object[] arrayList;

    public CustomArrayList() {
        this.arrayList = new Object[DEFAULT_SIZE];
    }

    public CustomArrayList(int size) {
        this.arrayList = new Object[size];
    }

    /**
     * To add an element into the ArrayList
     * 
     * @param value
     */
    public void add(T value) {
        if (isFull()) {
            resize();
        }
        this.arrayList[this.size++] = value;
    }

    /**
     * To remove and return the last element from the ArrayList
     * 
     * @return The last element from the ArrayList
     */
    public T remove() throws NoSuchElementException {
        if (isEmpty())
            throw new NoSuchElementException();
        T removed = (T) this.arrayList[--this.size];
        this.arrayList[this.size] = null;
        return removed;
    }

    /**
     * To get the size of the Array
     * 
     * @return Integer
     */
    public int size() {
        return this.size;
    }

    /**
     * To get the Array element present at the given index
     * 
     * @param index
     * @return the element at the specified position in this list
     */
    public T get(int index) {
        if (checkIndex(index))
            return (T) this.arrayList[index];
        return null;
    }

    /**
     * Checks if the given index is less than the length of the Array or not
     * 
     * @param index
     * @return Boolean
     */
    private boolean checkIndex(int index) {
        if (index < this.arrayList.length && index >= 0)
            return true;
        throw new IndexOutOfBoundsException("Array Index OutOfBound - ArrayList Size {" + this.arrayList.length + "}");
    }

    /**
     * To clear the ArrayList
     */
    public void clear() {
        this.arrayList = new Object[DEFAULT_SIZE];
        this.size = 0;
    }

    /**
     * To check and return true if the ArrayList is Empty
     * 
     * @return Boolean
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * To resize the Arraylist by doubling the current size
     */
    private void resize() {
        Object[] temp = Arrays.copyOf(this.arrayList, this.size * 2);
        this.arrayList = temp;
    }

    /**
     * To check if the Array is full or not
     * 
     * @return Boolean
     */
    private boolean isFull() {
        return this.arrayList.length == this.size;
    }

    @Override
    public String toString() {
        return "CustomArrayList [size=" + size + ", arrayList=" + Arrays.toString(arrayList) + "]";
    }

    public static void main(String[] args) {
        CustomArrayList<Integer> arr = new CustomArrayList<>(8);

        for (int i = 0; i < 12; i++) {
            arr.add(i * 2);
        }
        System.out.println("Array after Adding Values: " + arr);

        int removedEle = arr.remove();
        System.out.println("Removed Element: " + removedEle);
        System.out.println("Array after Removing Last Value: " + arr);

        arr.clear();
        System.out.println("Array after Clearing Data: " + arr);
    }
}
