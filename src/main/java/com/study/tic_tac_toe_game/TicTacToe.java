package com.study.tic_tac_toe_game;

import java.util.Scanner;

/**
 * Play Tic-Tac-Toe Game
 */
public class TicTacToe {
    private static final char CROSS = 'X';
    private static final char ZERO = 'O';

    private char[][] board = new char[3][3];
    private char player = 'X';
    private boolean isGameOver = false;

    /**
     * Constructor to Initialize the board
     */
    public TicTacToe() {
        for (int row = 0; row < this.board.length; row++) {
            for (int col = 0; col < this.board[0].length; col++) {
                this.board[row][col] = ' ';
            }
        }
        display();
    }

    /**
     * Starts the tic-tac-toe game and continues till its over
     */
    public void beginGame() {
        Scanner scanner = new Scanner(System.in);
        while (!this.isGameOver) {
            // Get user Inputs
            getUserInput(scanner);

            // Check if the current player won or not
            if (hasWon()) {
                System.out.println("Player " + this.player + " has Won the Game.");
                this.isGameOver = true;
            }
            // Check if the board is filled or not
            else if (isBoardFilled()) {
                System.out.println("Good Game! It is a Draw.");
                this.isGameOver = true;
            }
            // If the player has not won continue the game with other player's turn
            else {
                this.player = this.player == CROSS ? ZERO : CROSS;
            }

        }
        scanner.close();
    }

    /**
     * Checks if the given coordinates are valid to be placed on the board
     * 
     * @param row Integer
     * @param col Integer
     * @return Boolean - Are Valid Coordinates or Not
     */
    private boolean areValidCoordiantes(int row, int col) {
        return row >= 0 && col >= 0 && row < 3 && col < 3 && ' ' == this.board[row][col];
    }

    /**
     * Gets and validates the user input coordinates for the board to be filled
     * 
     * @param scanner Scanner
     */
    private void getUserInput(Scanner scanner) {
        boolean validInputs = false;
        // Loop until the inputs are correct
        while (!validInputs) {
            // Get user Inputs
            System.out.println("Player " + this.player + " [Provide the coordinates]: ");
            try {
                if (scanner.hasNextInt()) {
                    int row = scanner.nextInt() - 1;
                    if (scanner.hasNextInt()) {
                        int col = scanner.nextInt() - 1;
                        // Validate the coordinates provided
                        if (areValidCoordiantes(row, col)) {
                            validInputs = true;
                            this.board[row][col] = this.player;
                        } else {
                            System.out.println("Invalid Coordinates! Please Enter Again.");
                        }
                    } else {
                        System.out.println("Invalid Column! Please Enter Again.");
                        scanner.nextLine(); // Clear the invalid input
                    }
                } else {
                    System.out.println("Invalid Row! Please Enter Again.");
                    scanner.nextLine(); // Clear the invalid input
                }
            } catch (Exception e) {
                System.out.println("Invalid Input Error Encountered! Please Enter Again." + e);
                scanner.nextLine(); // Clear the buffer for invalid input
            }
        }

    }

    /**
     * Validates if any of the player has won the game or not
     * 
     * @return If the current player won
     */
    private boolean hasWon() {
        display();
        // Check Rows
        for (int row = 0; row < this.board.length; row++) {
            if (this.board[row][0] == this.player && this.board[row][1] == this.player
                    && this.board[row][2] == this.player) {
                return true;
            }
        }

        // Check Columns
        for (int col = 0; col < this.board.length; col++) {
            if (this.board[0][col] == this.player && this.board[1][col] == this.player
                    && this.board[2][col] == this.player) {
                return true;
            }
        }

        // Check Diagonals
        return this.board[1][1] == this.player
                && ((this.board[0][0] == this.player && this.board[2][2] == this.player) ||
                        (this.board[0][2] == this.player && this.board[2][0] == this.player));
    }

    /**
     * Checks if the board is completly filled or not
     * 
     * @return Is Board completed
     */
    private boolean isBoardFilled() {
        for (int row = 0; row < this.board.length; row++) {
            for (int col = 0; col < this.board[0].length; col++) {
                if (this.board[row][col] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Displays the current state of the Board
     */
    public void display() {
        System.out.println("Board: ");
        for (int row = 0; row < this.board.length; row++) {
            for (int col = 0; col < this.board[0].length; col++) {
                System.out.print("| " + this.board[row][col] + (col == this.board[0].length - 1 ? " |" : " "));
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        TicTacToe board = new TicTacToe();
        board.beginGame();
    }

}
