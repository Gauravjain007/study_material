package com.study.dsaPractice.popularProblems;

import com.study.custom_collections.CustomCircularLinkedList;

public class JosephusProblem {

    /**
     * The Josephus problem is a famous problem in computer science, in which
     * n people are standing in a circle and every kth person is executed. The
     * problem is to find the position of the last person standing.
     * 
     * Simulates the Josephus problem using a custom circular linked list.
     *
     * @param n The number of people in the circle.
     * @param k The interval between each person to be executed.
     * @return The position of the last person standing.
     * @throws IllegalArgumentException if n and k are not greater than 0.
     */
    public static char josephus(int n, int k) {
        System.out.println(String.format("Josephus(%s, %s) Simulation:", n, k));
        if (n <= 0 || k <= 0) {
            throw new IllegalArgumentException("n and k must be greater than 0");
        }

        CustomCircularLinkedList<Character> circle = new CustomCircularLinkedList<>();

        // Add people to circle
        for (int i = 0; i < n; i++) {
            circle.addLast((char) ('A' + i));
        }

        // Simulate the elimination process
        int position = 0;
        while (circle.size() > 1) {
            position = (position + k - 1) % circle.size();
            System.out.println("Removing: " + circle.remove(position));
            System.out.println("Current Circle: " + circle);
            if (position == circle.size()) {
                position = 0;
            }
        }

        return circle.get(0); // Last survivor
    }

    public static void main(String[] args) {
        System.out.println(josephus(7, 3));
    }
}
