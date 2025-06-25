package com.study.custom_collections;

import java.util.LinkedList;
import java.util.List;

public class CustomHashSet<T> {
    private LinkedList<T>[] set;
    private int size;
    private int capacity;

    private static final int DEFAULT_CAPACITY = 8;
    private static final double LOAD_FACTOR = 0.75;

    @SuppressWarnings("unchecked")
    public CustomHashSet() {
        this.capacity = DEFAULT_CAPACITY;
        this.size = 0;
        this.set = new LinkedList[DEFAULT_CAPACITY];
    }

    @SuppressWarnings("unchecked")
    public CustomHashSet(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.set = new LinkedList[capacity];
    }

    /**
     * Returns the index of the bucket that should contain the given value.
     * This method simply hashes the value and takes the modulus of the capacity.
     * 
     * @param value the value to be hashed
     * @return the index of the bucket that should contain the given value
     */
    private int getHashedIndex(T value) {
        return Math.absExact(value.hashCode()) % capacity;
    }

    /**
     * Resizes the internal array to double its current size, and rehashes all
     * elements currently in the set to their new positions in the resized array.
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        this.capacity *= 2; // Double the capacity
        LinkedList<T>[] oldSet = this.set;
        this.set = new LinkedList[this.capacity];

        this.size = 0; // Reset size, will be recalculated in the loop below
        for (LinkedList<T> list : oldSet) {
            if (list != null) {
                for (T value : list) {
                    add(value);
                }
            }
        }
    }

    /**
     * Adds the given value to the set if it is not already present.
     *
     * If the set is at capacity and the load factor is exceeded, the set will
     * be resized to double its current size before adding the element.
     *
     * @param value the value to be added
     * @return true if the value was added, false if the value was already present
     */
    public boolean add(T value) {
        if (contains(value)) {
            return false;
        }

        if (this.size >= this.capacity * LOAD_FACTOR) {
            resize();
        }

        int idx = getHashedIndex(value);
        if (this.set[idx] == null) {
            this.set[idx] = new LinkedList<>();
        }
        this.set[idx].add(value);
        this.size++;
        return true;
    }

    /**
     * Removes the given value from the set if it is present.
     *
     * @param value the value to be removed
     * @return true if the value was removed, false if the value was not present
     */
    public boolean remove(T value) {
        if (!contains(value)) {
            return false;
        }
        int idx = getHashedIndex(value);
        this.set[idx].remove(value);
        this.size--;
        return true;
    }

    /**
     * Checks if the given value is present in the set.
     *
     * @param value the value to be searched for
     * @return true if the value is present, false if the value is not present
     */
    public boolean contains(T value) {
        int idx = getHashedIndex(value);
        if (this.set[idx] == null) {
            return false;
        }
        return this.set[idx].contains(value);
    }

    /**
     * Returns true if the set is empty, false otherwise.
     * 
     * @return true if the set is empty, false otherwise
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Returns the number of elements in the set.
     *
     * @return the number of elements in the set
     */
    public int size() {
        return this.size;
    }

    /**
     * Returns a list containing all elements in the set. The returned list is not
     * backed by the set, so changes to the returned list are not reflected in the
     * set and vice versa.
     *
     * @return a list containing all elements in the set
     */
    public List<T> toList() {
        List<T> linkedList = new LinkedList<>();
        for (LinkedList<T> list : set) {
            if (list != null) {
                linkedList.addAll(list);
            }
        }
        return linkedList;
    }

    @Override
    public String toString() {
        return String.valueOf(this.toList());
    }

    public static void main(String[] args) {
        CustomHashSet<Character> set = new CustomHashSet<>(4);
        set.add('A');
        set.add('B');
        set.add('C');
        set.add('A');
        System.out.println(set);
        System.out.println("Contains 'A': " + set.contains('A'));
        System.out.println("Removed 'B': " + set.remove('B'));
        System.out.println("Contains 'B': " + set.contains('B'));
        System.out.println("Size: " + set.size());
        System.out.println("Is Empty: " + set.isEmpty());
        System.out.println(set.toList());
        set.add('D');
        set.add('E');
        set.add('F');
        System.out.println("Size: " + set.size());
        System.out.println(set);
    }
}
