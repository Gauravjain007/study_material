package com.study.custom_collections;

public class CustomCircularLinkedList<T> {
    private int size = 0;
    private Node<T> head;
    private Node<T> tail;

    private static final class Node<T> {
        private T value;
        private Node<T> next;

        public Node(T value) {
            this.value = value;
            this.next = null;
        }

        @Override
        public String toString() {
            return "Node [" + this.value + "]";
        }
    }

    /**
     * Adds the given value at the beginning of the Circular Linked List.
     * 
     * @param value the value to be added
     */
    public void addFirst(T value) {
        Node<T> newNode = new Node<>(value);
        if (this.head == null) {
            this.head = newNode;
            this.tail = newNode;
            this.tail.next = newNode;
        } else {
            newNode.next = this.head;
            this.tail.next = newNode;
            this.head = newNode;
        }
        size++;
    }

    /**
     * Adds the given value at the end of the Circular Linked List.
     * 
     * @param value the value to be added
     */
    public void addLast(T value) {
        Node<T> newNode = new Node<>(value);
        if (this.head == null) {
            this.head = newNode;
            this.tail = newNode;
            this.tail.next = newNode;
        } else {
            newNode.next = this.head;
            this.tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    /**
     * Adds the given value at the given index of the Circular Linked List.
     * 
     * @param value the value to be added
     * @param index the index at which the value is to be added
     * @throws IndexOutOfBoundsException if the given index is not valid
     */
    public void add(T value, int index) {
        if (!isValidIndex(index))
            throw new IndexOutOfBoundsException("Invalid Index");

        Node<T> newNode = new Node<>(value);

        if (index == 0)
            addFirst(value);
        else if (index == size)
            addLast(value);
        else {
            Node<T> target = findNodeAtIndex(index - 1);
            newNode.next = target.next;
            target.next = newNode;
        }
        size++;
    }

    /**
     * Removes the first element from the Circular Linked List.
     * 
     * @return the value of the removed element
     * @throws IndexOutOfBoundsException if the list is empty
     */
    public T removeFirst() {
        if (head == null)
            throw new IndexOutOfBoundsException("List is Empty");

        T removedValue = this.head.value;

        this.tail.next = this.head.next;
        this.head = this.head.next;
        size--;
        return removedValue;
    }

    /**
     * Removes the last element from the Circular Linked List.
     * 
     * @return the value of the removed element
     * @throws IndexOutOfBoundsException if the list is empty
     */
    public T removeLast() {
        if (head == null)
            throw new IndexOutOfBoundsException("List is Empty");

        T removedValue = this.tail.value;

        Node<T> target = findNodeAtIndex(this.size - 2);
        target.next = this.head;
        this.tail = target;
        size--;
        return removedValue;
    }

    /**
     * Removes the element at the given index from the Circular Linked List.
     * 
     * @param index the index of the element to be removed
     * @return the value of the removed element
     * @throws IndexOutOfBoundsException if the given index is not valid
     */
    public T remove(int index) {
        if (head == null)
            throw new IndexOutOfBoundsException("List is Empty");
        if (!isValidIndex(index))
            throw new IndexOutOfBoundsException("Invalid Index");

        T removedValue;
        if (index == 0)
            removedValue = removeFirst();
        else if (index == size - 1)
            removedValue = removeLast();
        else {
            Node<T> target = findNodeAtIndex(index - 1);
            removedValue = target.next.value;

            target.next = target.next.next;
            size--;
        }
        return removedValue;
    }

    /**
     * Returns the element at the given index from the Circular Linked List.
     * 
     * @param index the index of the element to be returned
     * @return the value of the element at the given index
     * @throws IndexOutOfBoundsException if the given index is not valid
     */
    public T get(int index) {
        if (!isValidIndex(index))
            throw new IndexOutOfBoundsException("Invalid Index");

        Node<T> target = findNodeAtIndex(index);
        return target.value;
    }

    /**
     * Returns the number of elements in the Circular Linked List.
     * 
     * @return the size of the Circular Linked List
     */
    public int size() {
        return this.size;
    }

    private Node<T> findNodeAtIndex(int index) {
        Node<T> curr = head;
        int currIndex = 0;
        while (currIndex != index || curr.next == null) {
            curr = curr.next;
            currIndex++;
        }
        return curr;
    }

    private boolean isValidIndex(int index) {
        return index >= 0 && index <= this.size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node<T> curr = head;
        if (curr != null) {
            do {
                sb.append(curr.value).append(" -> ");
                curr = curr.next;
            } while (curr != head);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        CustomCircularLinkedList<Integer> list = new CustomCircularLinkedList<>();
        list.addFirst(1);
        list.addLast(2);
        System.out.println(list);
        list.add(3, 1);
        System.out.println(list);
        System.out.println("Removed First: " + list.removeFirst());
        System.out.println(list);
        System.out.println("Removed Last: " + list.removeLast());
        System.out.println(list);
        list.addLast(4);
        System.out.println(list);
        System.out.println("Removed at Index 1: " + list.remove(1));
        list.add(5, 1);
        System.out.println(list);

    }

}