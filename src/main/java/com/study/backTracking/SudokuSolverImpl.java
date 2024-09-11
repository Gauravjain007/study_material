package com.study.backTracking;

/**
 * Question: {@link https://leetcode.com/problems/sudoku-solver/}
 */
public class SudokuSolverImpl {

    private static final int SIZE = 9;
    private static final char EMPTY_CELL = 0;
    private static final int BLOCK_SIZE = 3;

    public static void main(String[] args) {
        int[][] board = {
                { 5, 3, 0, 0, 7, 0, 0, 0, 0 },
                { 6, 0, 0, 1, 9, 5, 0, 0, 0 },
                { 0, 9, 8, 0, 0, 0, 0, 6, 0 },
                { 8, 0, 0, 0, 6, 0, 0, 0, 3 },
                { 4, 0, 0, 8, 0, 3, 0, 0, 1 },
                { 7, 0, 0, 0, 2, 0, 0, 0, 6 },
                { 0, 6, 0, 0, 0, 0, 2, 8, 0 },
                { 0, 0, 0, 4, 1, 9, 0, 0, 5 },
                { 0, 0, 0, 0, 8, 0, 0, 7, 9 }
        };

        System.out.println("Original Board: ");
        printBoard(board);
        if (solveSudoku(board, 0, 0)) {
            System.out.println("\nSolved Sudoku Board: ");
            printBoard(board);
        }
    }

    /**
     * Solves the Sudoku 9 x 9 board using <b>Backtracking</b>
     * 
     * @param board Integer[SIZE][SIZE]
     * @param row   Integer
     * @param col   Integer
     * @return Boolean
     */
    public static boolean solveSudoku(int[][] board, int row, int col) {
        // If we completed the Sudoku board
        if (row == SIZE - 1 && col == SIZE) {
            return true;
        }
        // If we reach the Last Column of the Row
        // Move to the next Row
        if (col == SIZE) {
            return solveSudoku(board, row + 1, 0);
        }
        // If the Cell is already Filled
        // Move to the next Column
        if (board[row][col] != EMPTY_CELL) {
            return solveSudoku(board, row, col + 1);
        }
        // Hit and Trial Method to check if the number is safe to be placed at
        // board[row][col]
        for (int number = 1; number <= SIZE; number++) {
            if (isSafe(board, row, col, number)) {
                // If yes then place it
                board[row][col] = number;
                // Move to the next cell
                // If the returned value is true means that sudoku is solved
                if (solveSudoku(board, row, col + 1)) {
                    return true;
                }
                // If the current path was not correct
                // Then revert the applied change for next Iteration
                board[row][col] = EMPTY_CELL;
            }
        }

        return false;
    }

    /**
     * Checks if the value at {@code board[row][col]} is valid or not
     * 
     * @param board Integer[SIZE][SIZE]
     * @param row   Integer
     * @param col   Integer
     * @param num   Integer
     * @return Boolean - Is Safe or Not
     */
    private static boolean isSafe(int[][] board, int row, int col, int num) {
        // Checks if the value is present in the entire row or column
        // If found return false
        for (int i = 0; i < SIZE; i++) {
            if (board[i][col] == num || board[row][i] == num)
                return false;
        }
        // Checks if the value is present in the 3 x 3 Box
        // If found return false
        int boxr = (row / BLOCK_SIZE) * BLOCK_SIZE;
        int boxc = (col / BLOCK_SIZE) * BLOCK_SIZE;
        for (int r = boxr; r < boxr + BLOCK_SIZE; r++) {
            for (int c = boxc; c < boxc + BLOCK_SIZE; c++) {
                if (board[r][c] == num) {
                    return false;
                }
            }
        }
        // Else it is Safe to place the value at board[row][col]
        return true;
    }

    /**
     * Prints the Sudoku Board
     * 
     * @param board Integer[SIZE][SIZE]
     */
    private static void printBoard(int[][] board) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(board[i][j] + "  ");
            }
            System.out.println();
        }
    }

}
