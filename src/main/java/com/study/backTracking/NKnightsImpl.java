package com.study.backTracking;

public class NKnightsImpl {
    public static void main(String[] args) {
        int n = 3;
        boolean[][] board = new boolean[n][n];
        System.out.println("Total Possible Solutions: " + findNKnights(board, 0, 0, n));
    }

    /**
     * <pre>
     * To Find and Print the N x N Board with the N Knights placed such that no Knight
     * can attack each other.
     * Solution: Using Recursion and Backtracking
     * </pre>
     * 
     * @param board   Boolean[][]
     * @param row     Integer
     * @param col     Integer
     * @param knights Integer
     * @return Integer - Total Count
     */
    public static int findNKnights(boolean[][] board, int row, int col, int knights) {
        // Check if all knights have been positioned
        // If yes, then print the Board and return count 1
        if (knights == 0) {
            displayBoard(board);
            return 1;
        }
        // If we reach the last cell
        if (row == board.length - 1 && col == board.length)
            return 0;

        // If we reach the board's right end, then move to the next row
        if (col == board.length) {
            return findNKnights(board, row + 1, 0, knights);
        }

        int count = 0;
        // Check if the current positon is safe
        // If Yes, then reduce the knight count and move to the next column
        if (isSafe(board, row, col)) {
            board[row][col] = true; // Marking the position as Placed
            count += findNKnights(board, row, col + 1, knights - 1);
            board[row][col] = false; // Reverting the Marked Position for next Iteration
        }

        // If the position is not safe then move to the next column
        count += findNKnights(board, row, col + 1, knights);
        return count;
    }

    /**
     * Checks if the current position of the Knight is Safe to be placed or not.
     * 
     * @param board Boolean[][]
     * @param row   Integer
     * @param col   Integer
     * @return Boolean - Is Safe or Not
     */
    public static boolean isSafe(boolean[][] board, int row, int col) {
        // Check 2 rows above left
        if (isValid(board, row - 2, col - 1) && board[row - 2][col - 1])
            return false;
        // Check 2 rows above right
        if (isValid(board, row - 2, col + 1) && board[row - 2][col + 1])
            return false;
        // Check 1 row above left
        if (isValid(board, row - 1, col - 2) && board[row - 1][col - 2])
            return false;
        // Check 1 row above right
        if (isValid(board, row - 1, col + 2) && board[row - 1][col + 2])
            return false;

        // If safe
        return true;
    }

    /**
     * Checks the position is Out of Bound or Not
     * 
     * @param board Boolean[][]
     * @param row   Integer
     * @param col   Integer
     * @return Boolean - Is Valid or Not
     */
    private static boolean isValid(boolean[][] board, int row, int col) {
        return row >= 0 && row < board.length && col >= 0 && col < board.length;
    }

    /**
     * Prints the Board with the placed Knights Marked as True
     * 
     * @param board Boolean[][]
     */
    public static void displayBoard(boolean[][] board) {
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board.length; c++) {
                if (board[r][c])
                    System.out.print("K ");
                else
                    System.out.print("- ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
