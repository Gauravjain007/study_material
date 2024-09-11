package com.study.maze_problems;

/**
 * Solving Multiple Maze Problems
 */
public class MazeProblemImpl {

    private static final String START = " [START]";
    private static final String DESTINATION = " [DESTINATION]";

    public static void main(String[] args) {
        int rows = 3;
        int cols = 3;

        // Get No. of Paths to reach Destination
        int noOfPaths = countPaths(rows, cols);
        System.out.println("Total No. of Paths: " + noOfPaths);

        // Print Paths to reach Destination
        System.out.println("\nPrinting Paths of Maze: ");
        printPaths(START, rows, cols);

        // Print Paths to reach Destination [incl. Diagonal]
        System.out.println("\nPrinting Paths of Maze with Diagonal movement: ");
        printPathsWithDiagonal(START, rows, cols);

        // Print Paths to reach Destination [ignoring Obstacles]
        System.out.println("\nPrinting Paths of Maze ignoring Obstacles: ");
        int[][] maze = new int[rows][cols];
        maze[1][1] = 1; // Adding obstacles
        printPathsWithObstacles(START, maze, 0, 0);

        // Print Paths to reach Destination [All Directions]
        // ! Do not call this method
        // printPathsAllDirections(START, rows, cols);
    }

    /**
     * <b>Maze Problem:</b>
     * 
     * <pre>
     * Find the total number of Paths to reach at the [N x M]th Position of a Maze(2D Array) 
     * in which the only possible ways is to move - down or right.
     * </pre>
     * 
     * <b>Solution:</b>
     * 
     * <pre>
     * Using Simple recurion where on moving rightwards reduce the col by 1 
     * and reduce row by 1 on downwards movement.
     * </pre>
     * 
     * @param row Integer
     * @param col Integer
     * @return Integer - Total Count of Paths
     */
    public static int countPaths(int row, int col) {
        // Got one of the Path
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

    /**
     * <b>Maze Problem:</b>
     * 
     * <pre>
     * Solution to Print all the possible Paths to reach at the [N x M]th Position of a Maze(2D Array) 
     * in which the only possible ways is to move - down or right.
     * </pre>
     * 
     * @param p   String - Parsed Steps
     * @param row Integer
     * @param col Integer
     */
    public static void printPaths(String p, int row, int col) {
        // Found Destination
        if (row == 1 && col == 1) {
            System.out.println(p + DESTINATION);
            return;
        }
        // Moving downwards
        if (row > 1) {
            printPaths(p + "-> D", row - 1, col);
        }
        // Moving rightwards
        if (col > 1) {
            printPaths(p + "-> R", row, col - 1);
        }
    }

    /**
     * <b>Maze Problem:</b>
     * 
     * <pre>
     * Solution to Find and Print all the possible Paths to reach at the [N x M]th Position of a Maze(2D Array) 
     * in which the only possible ways is to move - horizontally, vertically and diagonally.
     * </pre>
     * 
     * @param p   String - Parsed Steps
     * @param row Integer
     * @param col Integer
     */
    public static void printPathsWithDiagonal(String p, int row, int col) {
        // Found Destination
        if (row == 1 && col == 1) {
            System.out.println(p + DESTINATION);
            return;
        }
        // Moving vertically
        if (row > 1) {
            printPathsWithDiagonal(p + "-> V", row - 1, col);
        }
        // Moving horizontally
        if (col > 1) {
            printPathsWithDiagonal(p + "-> H", row, col - 1);
        }
        // Moving diagonally
        if (row > 1 && col > 1) {
            printPathsWithDiagonal(p + "-> D", row - 1, col - 1);
        }
    }

    /**
     * <b>Maze with Obstacles Problem:</b>
     * 
     * <pre>
     * Solution to Find and Print all the possible Paths to reach at the [N x N]th Position of a Maze(2D Array) 
     * in which the only possible ways is to move - down or right. 
     * But you cannot go via Obstacles.
     * </pre>
     * 
     * @param p    String - Parsed Steps
     * @param maze Integer[][]
     * @param row  Integer
     * @param col  Integer
     */
    public static void printPathsWithObstacles(String p, int[][] maze, int row, int col) {
        // Met an obstacle, hence ignore this path
        if (maze[row][col] == 1)
            return;
        // Found Destination
        if (row == maze.length - 1 && col == maze[0].length - 1) {
            System.out.println(p + DESTINATION);
            return;
        }
        // Moving downwards
        if (row < maze.length - 1) {
            printPathsWithObstacles(p + "-> D", maze, row + 1, col);
        }
        // Moving rightwards
        if (col < maze[0].length - 1) {
            printPathsWithObstacles(p + "-> R", maze, row, col + 1);
        }
    }

    /**
     * <b>Maze Problem:</b>
     * <p>
     * !{@code Please ignore this solution and don't try to run it}
     * </p>
     * 
     * <pre>
     * Invalid Solution to Print all the possible Paths to reach at the [N x M]th Position of a Maze(2D Array) 
     * in which you can move in all 4 directions - Up, Down, Left, Right
     * ? Why is this an issue?
     * * Stack Overflow will occur [Not using Backtracking]
     * </pre>
     * 
     * @see com.study.maze_problems.BacktrackingMazeProblemImpl#printPathsAllDirectionsUsingBackTracking()
     *      BackTracking Solution
     * @param p   String - Parsed Steps
     * @param row Integer
     * @param col Integer
     */
    public static void printPathsAllDirections(String p, int row, int col) {
        // Found Destination
        if (row == 1 && col == 1) {
            System.out.println(p + DESTINATION);
            return;
        }
        // Moving down
        if (row > 1) {
            printPathsAllDirections(p + "-> D", row - 1, col);
        }
        // Moving right
        if (col > 1) {
            printPathsAllDirections(p + "-> R", row, col - 1);
        }

        // Moving up
        if (row > 1) {
            printPathsAllDirections(p + "-> U", row + 1, col);
        }

        // Moving left
        if (col > 1) {
            printPathsAllDirections(p + "-> L", row, col + 1);
        }
    }
}
