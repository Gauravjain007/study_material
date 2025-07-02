package com.study.basicLogicalProblems;

public class Fibonacci {

    /**
     * Prints the first n Fibonacci numbers.
     * 
     * <p>
     * Time complexity: O(n)
     * Space complexity: O(1)
     * 
     * @param n the number of Fibonacci numbers to print
     */
    public static void printFibonacci(int n) {
        int a = 0;
        int b = 1;
        for (int i = 0; i <= n; i++) {
            System.out.print(a + " ");
            int next = a + b;
            a = b;
            b = next;
        }
    }

    /**
     * Computes the nth Fibonacci number using recursion.
     * 
     * <p>
     * Time complexity: O(2^n)
     * Space complexity: O(n) due to the call stack from recursion
     * 
     * @param n the position in the Fibonacci sequence
     * @return the nth Fibonacci number
     */

    public static int fibonacci(int n) {
        if (n <= 1)
            return n;
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    /**
     * Prints the first n Fibonacci numbers using dynamic programming.
     * 
     * <p>
     * Time complexity: O(n)
     * Space complexity: O(n)
     * 
     * @param n the number of Fibonacci numbers to print
     */
    public static void fibonacciUsingDP(int n) {
        int[] fib = new int[n + 1];
        fib[0] = 0;
        fib[1] = 1;
        for (int i = 2; i <= n; i++) {
            fib[i] = fib[i - 1] + fib[i - 2];
        }
        for (int i = 0; i <= n; i++) {
            System.out.print(fib[i] + " ");
        }
    }

    public static void main(String[] args) {
        int n = 8;
        System.out.println("Fibonacci series up to " + n + ":");
        printFibonacci(n);
        System.out.println("\nFibonacci series up to " + n + " using recursion:");
        System.out.println(fibonacci(n));
        System.out.println("Fibonacci series up to " + n + " using dynamic programming:");
        fibonacciUsingDP(n);
    }
}
