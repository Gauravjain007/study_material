package com.study.custom_collections;

import java.util.Arrays;
import java.util.NoSuchElementException;

import com.study.exception_handling.MyException;

public class CustomStack<T> {
    private static final int DEFAULT_SIZE = 4;
    private static final int MAX_SIZE_LIMIT = 10;
    private int size = 0;
    private Object[] stack;

    public CustomStack() {
        this.stack = new Object[DEFAULT_SIZE];
    }

    public CustomStack(int size) {
        this.stack = new Object[size];
    }

    /**
     * To push an element into the Stack
     * 
     * @param value
     */
    public void push(T value) throws MyException {
        if (isFull()) {
            resize();
        }
        this.stack[this.size++] = value;
    }

    /**
     * To pop the last element from the Stack
     * 
     * @return The Popped element
     */
    public T pop() throws NoSuchElementException {
        if (isEmpty())
            throw new NoSuchElementException();
        T removed = (T) this.stack[--this.size];
        this.stack[this.size] = null;
        return removed;
    }

    /**
     * To get the last element from the Stack
     * 
     * @return The Last element of the Stack
     */
    public T peek() {
        if (isEmpty())
            return null;
        return (T) this.stack[this.size - 1];
    }

    /**
     * To resize the Stack by doubling the current size till the MAX_SIZE_LIMIT
     */
    private void resize() throws MyException {
        if (this.size >= MAX_SIZE_LIMIT)
            throw new MyException("Stack Overflow Occured");
        Object[] temp = Arrays.copyOf(this.stack, Math.min(this.size * 2, MAX_SIZE_LIMIT));
        this.stack = temp;
    }

    /**
     * To check if the Stack is full or not
     * 
     * @return Boolean
     */
    public boolean isFull() {
        return this.stack.length == this.size;
    }

    /**
     * To check and return true if the Stack is Empty
     * 
     * @return Boolean
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public String toString() {
        return "CustomStack [size=" + size + ", Stack=" + Arrays.toString(stack) + "]";
    }

    public static void main(String[] args) {
        try {
            CustomStack<String> stack = new CustomStack<>();
            System.out.println("Peek: " + stack.peek());
            System.out.println("Is empty: " + stack.isEmpty());
            stack.push("Hi");
            stack.push("This");
            stack.push("is");
            stack.push("Stack");
            stack.push("to be popped");
            System.out.println(stack);
            System.out.println("Popped Element: " + stack.pop());
            System.out.println(stack);
            System.out.println("Last Element: " + stack.peek());
            stack.push("1");
            stack.push("2");
            stack.push("3");
            stack.push("4");
            stack.push("5");
            stack.push("6");
            System.out.println("Is Full: " + stack.isFull());
            System.out.println(stack);
            System.out.println("Trying to insert another element");
            stack.push("7");

            System.out.println(stack);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}
