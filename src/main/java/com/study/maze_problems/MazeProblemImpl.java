package com.study.maze_problems;

/**
 * Solving Multiple Maze Problems
 */
public class MazeProblemImpl {

    public static void main(String[] args) {
        // Call countPaths with the Maze Rows and Columns
        int noOfPaths = countPaths(4, 3);
        System.out.println("Total No. of Paths: " + noOfPaths);
    }

    /**
     * <b>Maze Problem:</b>
     * Find the total number of Paths to each at the [N x M]th
     * Position of a Maze(2D Array) in which the only possible ways to move is to
     * downwards and rightwards.\n
     * <b>Solution:</b>
     * Using Simple recurion where on moving rightwards reduce the
     * col by 1 and reduce row by 1 on downwards movement.
     * 
     * @param row Integer - No. of Rows
     * @param col Integer - No. of Columns
     * @return Integer - Total Count of Paths
     */
    public static int countPaths(int row, int col) {
        if (row == 1 || col == 1) {
            return 1;
        } else {
            int count = 0;
            // Moving downwards
            count += countPaths(row - 1, col);
            // Moving rightwards
            count += countPaths(row, col - 1);
            return count;
        }
    }
}
