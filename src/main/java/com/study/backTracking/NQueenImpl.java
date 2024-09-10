package com.study.backTracking;

public class NQueenImpl {
    public static void main(String[] args) {
        int n = 4;
        boolean[][] board = new boolean[n][n];
        System.out.println("Total Possible Solutions: " + findNQueens(board, 0));
    }

    /**
     * <pre>
     * To Find and Print the N x N Board with the N Queens placed such that no queen
     * can attack each other.
     * Solution: Using Recursion and Backtracking
     * </pre>
     * 
     * @param board Boolean[][]
     * @param row   Integer
     * @return Integer - Total Count
     */
    public static int findNQueens(boolean[][] board, int row) {
        // Check if we reached at the last row
        // If yes, then print the Board and return count 1
        if (row == board.length) {
            displayBoard(board);
            return 1;
        } else {
            int count = 0;
            // Trying to place the Queen at each Column of the Row
            for (int col = 0; col < board.length; col++) {
                // Checking if the current position is Safe or Not
                // If yes, then find the position of a Queen in the next Row
                if (isSafe(board, row, col)) {
                    board[row][col] = true; // Marking the position as Placed
                    count += findNQueens(board, row + 1);
                    board[row][col] = false; // Reverting the Marked Position for next Iteration
                }
            }
            return count;
        }
    }

    /**
     * Checks if the current position of the Queen is Safe to be placed or not.
     * 
     * @param board Boolean[][]
     * @param row   Integer
     * @param col   Integer
     * @return Boolean - Is Safe
     */
    public static boolean isSafe(boolean[][] board, int row, int col) {
        for (int r = row - 1, left = col - 1, right = col + 1; r >= 0; r--, left--, right++) {
            if (board[r][col]) // Check the same column
                return false;
            if (left >= 0 && board[r][left]) // Check the left diagonal
                return false;
            if (right < board.length && board[r][right]) // Check the right diagonal
                return false;
        }
        // If safe
        return true;
    }

    /**
     * Prints the Board with the placed Queens Marked as True
     * 
     * @param board Boolean[][]
     */
    public static void displayBoard(boolean[][] board) {
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board.length; c++) {
                if (board[r][c])
                    System.out.print("Q ");
                else
                    System.out.print("- ");
            }
            System.out.println();
        }
        System.out.println();
    }

}
