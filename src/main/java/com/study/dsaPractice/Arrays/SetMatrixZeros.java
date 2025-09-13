package com.study.dsaPractice.Arrays;

import java.util.Arrays;

/**
 * Problem: https://leetcode.com/problems/set-matrix-zeroes/
 */
public class SetMatrixZeros {

    public static boolean findZeros(int[][] matrix, int m, int n) {

        boolean isfirstColZero = false;
        // Traverse the matrix
        for (int i = 0; i < m; i++) {
            // If first column has zero
            if (matrix[i][0] == 0)
                isfirstColZero = true;
            // Check if rest of the row has zero
            for (int j = 1; j < n; j++) {
                // If matrix[i][j] is zero mark first element of row and column as zero
                if (matrix[i][j] == 0) {
                    matrix[0][j] = 0;
                    matrix[i][0] = 0;
                }
            }
        }

        return isfirstColZero;
    }

    public static void setZeroes(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return;
        }
        int m = matrix.length;
        int n = matrix[0].length;

        boolean isfirstColZero = findZeros(matrix, m, n);

        // Traverse the matrix in reverse
        for (int i = m - 1; i >= 0; i--) {
            // No need to check for first column
            for (int j = n - 1; j >= 1; j--) {
                // If the first element of row or column is zero mark current element as zero
                if (matrix[0][j] == 0 || matrix[i][0] == 0) {
                    matrix[i][j] = 0;
                }
            }
            // If any element of first column is zero mark first element of each row as zero
            if (isfirstColZero) {
                matrix[i][0] = 0;
            }
        }
    }

    public static void main(String[] args) {
        int[][] matrix = { { 1, 1, 0 }, { 1, 1, 1 }, { 1, 1, 1 } };
        setZeroes(matrix);
        System.out.println("Matrix: ");
        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
    }
}
