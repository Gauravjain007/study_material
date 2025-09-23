package com.study.dsaPractice.Arrays;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PascalsTriangle {

    public static void printPascalTriangle(int numRows) {

        List<List<Integer>> triangle = new ArrayList<>();

        for (int i = 0; i < numRows; i++) {
            List<Integer> currentRow = new ArrayList<>();

            for (int j = 0; j <= i; j++) {
                if (j == 0 || j == i) {
                    // The first and last elements of each row are 1
                    currentRow.add(1);
                } else {
                    // Each inner element is the sum of the two elements directly above it
                    int value = triangle.get(i - 1).get(j - 1) + triangle.get(i - 1).get(j);
                    currentRow.add(value);
                }
            }

            triangle.add(currentRow);
        }

        // Print the triangle with proper formatting
        for (int i = 0; i < numRows; i++) {
            // Add leading spaces for alignment
            for (int k = 0; k < numRows - i - 1; k++) {
                System.out.print("  ");
            }
            for (int j = 0; j < triangle.get(i).size(); j++) {
                System.out.printf("%4d", triangle.get(i).get(j)); // Format for consistent spacing
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of rows for Pascal's Triangle: ");
        int numRows = scanner.nextInt();
        scanner.close();

        printPascalTriangle(numRows);
    }
}
