package com.study.custom_collections;

import java.util.Arrays;
import java.util.NoSuchElementException;

import com.study.exception_handling.MyException;

public class CustomCircularQueue<T> {
    private static final int DEFAULT_SIZE = 4;
    private static final int MAX_SIZE_LIMIT = 10;

    private int size = 0;
    private int front = 0;
    private int rear = 0;

    private Object[] queue;

    public CustomCircularQueue() {
        this.queue = new Object[DEFAULT_SIZE];
    }

    public CustomCircularQueue(int size) {
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
        this.queue[this.rear++] = value;
        this.rear = (this.rear) % this.queue.length;
        this.size++;
        System.out.println("Inserted Value: " + value);
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
        T removed = (T) this.queue[this.front];
        this.front = (++this.front) % this.queue.length;
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
        return (T) this.queue[this.front];
    }

    /**
     * To resize the queue by doubling the current size till the MAX_SIZE_LIMIT
     * And reassign the front and rear references
     */
    private void resize() throws MyException {
        if (this.size >= MAX_SIZE_LIMIT)
            throw new MyException("Queue Limit Reached");
        Object[] temp = new Object[Math.min(this.size * 2, MAX_SIZE_LIMIT)];
        int i = 0;
        int f = this.front;
        do {
            temp[i++] = this.queue[f];
            f = (++f) % this.queue.length;
        } while (i != this.size);
        this.front = 0;
        this.rear = this.size;
        this.queue = temp;
    }

    /**
     * To check if the queue is full or not
     * 
     * @return Boolean
     */
    public boolean isFull() {
        return this.queue.length <= this.size + 1;
    }

    /**
     * To check and return true if the queue is Empty
     * 
     * @return Boolean
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * To display the Circular Queue in Customed Manner
     * Values will be printed from front till rear
     */
    public void display() {
        System.out.print("CircularQueue: [ ");
        int i = front;
        do {
            System.out.print(this.queue[i] + " <- ");
            i = (++i) % this.queue.length;
        } while (i != this.rear);
        System.out.println("END ] - Front: " + front + ", Rear: " + rear + ", Size: " + size);
    }

    @Override
    public String toString() {
        return "CustomQueue [size=" + size + ", Queue=" + Arrays.toString(queue) + "]";
    }

    public static void main(String[] args) {
        try {
            CustomCircularQueue<String> queue = new CustomCircularQueue<>();
            System.out.println("Peek: " + queue.peek());
            System.out.println("Is empty: " + queue.isEmpty());
            queue.enqueue("Hi");
            queue.enqueue("This");
            queue.display();
            queue.enqueue("is");
            queue.enqueue("queue");
            queue.display();
            queue.enqueue("impl");
            queue.display();
            System.out.println("Removed Element: " + queue.dequeue());
            queue.display();
            System.out.println("Front Element: " + queue.peek());
            queue.enqueue("1");
            queue.display();
            queue.enqueue("2");
            queue.display();
            queue.enqueue("3");
            queue.enqueue("4");
            queue.display();
            queue.enqueue("5");
            queue.enqueue("6");
            System.out.println("Is Full: " + queue.isFull());
            queue.display();
            System.out.println(queue.dequeue());
            queue.display();
            queue.enqueue("last insert");
            System.out.println("Trying to insert another element");
            queue.enqueue("7");

            queue.display();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}
