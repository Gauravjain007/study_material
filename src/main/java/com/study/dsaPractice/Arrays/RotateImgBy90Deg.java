package com.study.dsaPractice.Arrays;

import java.util.Arrays;

/**
 * Problem: https://leetcode.com/problems/rotate-image/
 */
public class RotateImgBy90Deg {

    /**
     * Rotate a given matrix by 90 degrees clockwise.
     * <p>
     * This function uses an in-place approach, i.e., it does not require any
     * additional memory.
     * <p>
     * It works by first swapping the elements of the matrix in a way that
     * transposes the matrix, and then reversing each row of the matrix to get the
     * final rotated matrix.
     * 
     * @param matrix the input matrix to be rotated
     */
    public static void rotate(int[][] matrix) {
        int n = matrix.length;

        // Transpose the matrix (swap rows with columns)
        for (int row = 0; row < n; row++) {
            for (int col = row + 1; col < n; col++) {
                int tmp = matrix[row][col];
                matrix[row][col] = matrix[col][row];
                matrix[col][row] = tmp;
            }
        }

        // Reverse each row
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n / 2; col++) {
                int tmp = matrix[row][col];
                matrix[row][col] = matrix[row][n - 1 - col];
                matrix[row][n - 1 - col] = tmp;
            }
        }
    }

    public static void main(String[] args) {
        int[][] matrix = new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
        rotate(matrix);
        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
    }
}
