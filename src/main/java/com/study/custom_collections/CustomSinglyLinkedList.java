package com.study.custom_collections;

import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Implements Custom Singly Linkedlist of the Collections Framework
 */
public class CustomSinglyLinkedList<T> {

    private Node head;
    private Node tail;
    private int size;

    /**
     * Node consists of a Value and a reference of the Next Node
     */
    class Node {
        private T value;
        private Node next;

        public Node(T value) {
            this.value = value;
        }

        public Node(T value, Node next) {
            this.value = value;
            this.next = next;
        }

        @Override
        public String toString() {
            return "Node [" + value + ", " + next + "]";
        }
    }

    /**
     * Constructor to initialize the LinkedList
     */
    public CustomSinglyLinkedList() {
        this.size = 0;
    }

    /**
     * Inserts an Element at the first Index
     * 
     * @param value
     */
    public void insertFirst(T value) {
        Node newNode = new Node(value, this.head);
        this.head = newNode;

        if (this.tail == null) {
            this.tail = this.head;
        }

        this.size++;
    }

    /**
     * Inserts an Element at the last Index
     * 
     * @param value
     */
    public void insertLast(T value) {
        if (this.tail == null) {
            insertFirst(value);
            return;
        }
        Node newNode = new Node(value);
        this.tail.next = newNode;
        this.tail = newNode;

        this.size++;
    }

    /**
     * Inserts an Element at the given Index
     * 
     * @param index
     * @param value
     * @throws IndexOutOfBoundsException
     */
    public void insertAtIndex(int index, T value) throws IndexOutOfBoundsException {
        if (!isValidIndex(index))
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.size);
        else {
            if (index == 0) {
                insertFirst(value);
                return;
            }
            if (index == this.size) {
                insertLast(value);
                return;
            }
            Node temp = get(index - 1);
            Node newNode = new Node(value, temp.next);
            temp.next = newNode;

            this.size++;
        }
    }

    /**
     * Deletes the first Element if exists
     * And returns the Deleted Value
     * 
     * @return value of the given type
     * @throws NoSuchElementException
     */
    public T deleteFirst() throws NoSuchElementException {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }

        T value = this.head.value;
        this.head = this.head.next;

        if (this.head == null) {
            this.tail = null;
        }

        size--;

        return value;
    }

    /**
     * Deletes the last Element if exists
     * And returns the Deleted Value
     * 
     * @return value of the given type
     * @throws NoSuchElementException
     */
    public T deleteLast() throws NoSuchElementException {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        if (this.size == 1) {
            return deleteFirst();
        }

        T value = tail.value;

        Node newTail = get(this.size - 2);
        newTail.next = null;
        tail = newTail;
        size--;

        return value;
    }

    /**
     * Deletes the Element at the given Index if exists
     * And returns the Deleted Value
     * 
     * @param index
     * @return value of the given type
     * @throws NoSuchElementException
     * @throws IndexOutOfBoundsException
     */
    public T deleteAtIndex(int index) throws NoSuchElementException, IndexOutOfBoundsException {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        if (!isValidIndex(index) || index == this.size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.size);
        if (index == 0) {
            return deleteFirst();
        }
        if (index == this.size - 1) {
            return deleteLast();
        }

        Node prevNode = get(index - 1);
        T value = prevNode.next.value;
        prevNode.next = prevNode.next.next;
        size--;

        return value;
    }

    /**
     * Returns the Node at the given index if exists
     * 
     * @param index
     * @return value of the given type
     * @throws IndexOutOfBoundsException
     */
    public Node get(int index) throws IndexOutOfBoundsException {
        if (!isValidIndex(index)) {
            throw new IndexOutOfBoundsException();
        }
        Node temp = this.head;
        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }
        return temp;
    }

    /**
     * Finds the given value in the LinkedList
     * And returns the Node if found otherwise returns null
     * 
     * @param value
     * @return Node
     */
    public Node find(T value) {
        Node temp = this.head;
        for (int i = 0; i < this.size; i++) {
            if (value == temp.value) {
                return temp;
            }
            temp = temp.next;
        }
        return null;
    }

    /**
     * Finds the given value in the LinkedList
     * And returns the index if found otherwise returns -1
     * 
     * @param value
     * @return
     */
    public int findIndex(T value) {
        Node temp = this.head;
        for (int i = 0; i < this.size; i++) {
            if (value == temp.value) {
                return i;
            }
            temp = temp.next;
        }
        return -1;
    }

    /**
     * Checks if the given index is Valid or not
     * 
     * @param index
     * @return true if valid
     */
    private boolean isValidIndex(int index) {
        return index >= 0 && index <= this.size;
    }

    /**
     * Prints the LinkedList in a Customed Manner
     */
    public void display() {
        Node temp = this.head;
        System.out.print("[ ");
        while (temp != null) {
            System.out.print(temp.value + " -> ");
            temp = temp.next;
        }
        System.out.println("END ] - size{" + this.size + "}");
    }

    @Override
    public String toString() {
        return "CustomSinglyLinkedList [head=" + head + ", tail=" + tail + ", size=" + size + "]";
    }

    public static void main(String[] args) {
        CustomSinglyLinkedList<Integer> ll = new CustomSinglyLinkedList<>();
        ll.insertFirst(10);
        ll.insertFirst(20);
        ll.insertLast(30);
        ll.insertAtIndex(2, 50);
        ll.display();
        System.out.println("Deleted " + ll.deleteFirst());
        ll.display();
        System.out.println("Deleted " + ll.deleteLast());
        ll.display();
        ll.insertLast(30);
        ll.display();
        ll.insertAtIndex(2, 40);
        ll.display();
        System.out.println("Deleted " + ll.deleteAtIndex(2));
        ll.display();
        System.out.println(ll.find(null));
        System.out.println(ll.findIndex(10));
        System.out.println(ll.findIndex(30));
    }
}
