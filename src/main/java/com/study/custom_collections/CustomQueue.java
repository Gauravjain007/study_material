package com.study.custom_collections;

import java.util.Arrays;
import java.util.NoSuchElementException;

import com.study.exception_handling.MyException;

public class CustomQueue<T> {
    private static final int DEFAULT_SIZE = 4;
    private static final int MAX_SIZE_LIMIT = 10;
    private int size = 0;
    private Object[] queue;

    public CustomQueue() {
        this.queue = new Object[DEFAULT_SIZE];
    }

    public CustomQueue(int size) {
        this.queue = new Object[size];
    }

    /**
     * To insert an element into the queue
     * 
     * @param value
     */
    public void enqueue(T value) throws MyException {
        if (isFull()) {
            resize();
        }
        this.queue[this.size++] = value;
    }

    /**
     * To remove the last element from the queue
     * 
     * @return The Popped element
     */
    @SuppressWarnings("unchecked")
    public T dequeue() throws NoSuchElementException {
        if (isEmpty())
            throw new NoSuchElementException();
        T removed = (T) this.queue[0];

        // Shifts the elements [creates a copy from index 1 till queue length + 1]
        Object[] temp = Arrays.copyOfRange(this.queue, 1, this.queue.length + 1);
        this.queue = temp;
        this.size--;
        return removed;
    }

    /**
     * To get the Front element of the queue
     * 
     * @return The Front element of the queue
     */
    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty())
            return null;
        return (T) this.queue[0];
    }

    /**
     * To resize the queue by doubling the current size till the MAX_SIZE_LIMIT
     */
    private void resize() throws MyException {
        if (this.size >= MAX_SIZE_LIMIT)
            throw new MyException("Queue Limit Reached");
        Object[] temp = Arrays.copyOf(this.queue, Math.min(this.size * 2, MAX_SIZE_LIMIT));
        this.queue = temp;
    }

    /**
     * To check if the queue is full or not
     * 
     * @return Boolean
     */
    public boolean isFull() {
        return this.queue.length == this.size;
    }

    /**
     * To check and return true if the queue is Empty
     * 
     * @return Boolean
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public String toString() {
        return "CustomQueue [size=" + size + ", Queue=" + Arrays.toString(queue) + "]";
    }

    public static void main(String[] args) {
        try {
            CustomQueue<String> queue = new CustomQueue<>();
            System.out.println("Peek: " + queue.peek());
            System.out.println("Is empty: " + queue.isEmpty());
            queue.enqueue("Hi");
            queue.enqueue("This");
            queue.enqueue("is");
            queue.enqueue("queue");
            queue.enqueue("impl");
            System.out.println(queue);
            System.out.println("Removed Element: " + queue.dequeue());
            System.out.println(queue);
            System.out.println("Front Element: " + queue.peek());
            queue.enqueue("1");
            queue.enqueue("2");
            queue.enqueue("3");
            queue.enqueue("4");
            queue.enqueue("5");
            queue.enqueue("6");
            System.out.println("Is Full: " + queue.isFull());
            System.out.println(queue);
            System.out.println("Trying to insert another element");
            queue.enqueue("7");

            System.out.println(queue);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}
