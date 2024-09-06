package com.study.maze_problems;

import java.util.Arrays;

public class BacktrackingMazeProblemImpl {
    public static void main(String[] args) {
        int rows = 3;
        int cols = 3;

        System.out.println("Printing Paths of Maze with all Directions: ");
        boolean[][] maze = new boolean[rows][cols];
        printPathsAllDirectionsUsingBackTracking("[START]", 0, 0, maze);

        System.out.println("\nPrinting Matrix Paths of Maze with all Directions: ");
        int[][] stepMaze = new int[rows][cols];
        printPathMatrixUsingBackTracking("[START]", 0, 0, stepMaze, 0);
    }

    /**
     * <b>Maze Problem:</b>
     * 
     * <pre>
     * Solution: 
     * Prints all the possible Paths to reach at the [N x M]th Position of a Maze(2D Array) 
     * in which you can move in all 4 directions - Up, Down, Left, Right
     * * Using Backtracking
     * </pre>
     * 
     * @param p    String - Parsed Steps
     * @param row  Integer
     * @param col  Integer
     * @param maze Boolean[][] - tracks visits
     */
    public static void printPathsAllDirectionsUsingBackTracking(String p, int row, int col, boolean[][] maze) {
        // If already visited
        if (maze[row][col]) {
            return;
        }
        // If not Mark the position as visited
        maze[row][col] = true;
        // Found Destination
        if (row == maze.length - 1 && col == maze[0].length - 1) {
            System.out.println(p + " [DESTINATION]");
        }
        // Moving dowm
        if (row < maze.length - 1) {
            printPathsAllDirectionsUsingBackTracking(p + "-> D", row + 1, col, maze);
        }
        // Moving right
        if (col < maze[0].length - 1) {
            printPathsAllDirectionsUsingBackTracking(p + "-> R", row, col + 1, maze);
        }
        // Moving up
        if (row > 0) {
            printPathsAllDirectionsUsingBackTracking(p + "-> U", row - 1, col, maze);
        }
        // Moving left
        if (col > 0) {
            printPathsAllDirectionsUsingBackTracking(p + "-> L", row, col - 1, maze);
        }

        // While Returing revert the maze to the original state
        // Mark the current position as unvisited for the next path
        maze[row][col] = false;
    }

    /**
     * <b>Maze Problem:</b>
     * 
     * <pre>
     * Solution: 
     * Prints the Matrix with all the possible Paths to reach at the [N x M]th Position of a Maze(2D Array) 
     * in which you can move in all 4 directions - Up, Down, Left, Right
     * * Using Backtracking
     * </pre>
     * 
     * @param p    String - Parsed Steps
     * @param row  Integer
     * @param col  Integer
     * @param maze Integer[][] - Tracks visits and steps
     * @param step Integer
     */
    public static void printPathMatrixUsingBackTracking(String p, int row, int col, int[][] maze, int step) {
        // If already visited
        if (maze[row][col] != 0) {
            return;
        }
        // If not Mark the step at position as visited
        maze[row][col] = step++;
        // Found Destination
        if (row == maze.length - 1 && col == maze[0].length - 1) {
            System.out.println(p + " [DESTINATION]");
            for (int[] arr : maze) {
                System.out.println(Arrays.toString(arr));
            }
        }
        // Moving dowm
        if (row < maze.length - 1) {
            printPathMatrixUsingBackTracking(p + "-> D", row + 1, col, maze, step);
        }
        // Moving right
        if (col < maze[0].length - 1) {
            printPathMatrixUsingBackTracking(p + "-> R", row, col + 1, maze, step);
        }
        // Moving up
        if (row > 0) {
            printPathMatrixUsingBackTracking(p + "-> U", row - 1, col, maze, step);
        }
        // Moving left
        if (col > 0) {
            printPathMatrixUsingBackTracking(p + "-> L", row, col - 1, maze, step);
        }

        // While Returing revert the maze to the original state
        // Mark the current position as unvisited for the next path
        maze[row][col] = 0;
    }
}
