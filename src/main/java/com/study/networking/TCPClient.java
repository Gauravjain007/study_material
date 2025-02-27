package com.study.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TCPClient {

    private static final int PORT = 2001;
    private static final String EXIT_MESSAGE = "exit";
    private static final String ERROR = "Error: ";

    /**
     * This is the main method of the TCPClient class. It creates a socket to
     * connect to the server and waits for a client to connect. Once a client
     * connects, it creates two threads. The first thread reads messages from
     * the server and prints them to the console. The second thread reads
     * messages from the console and sends them to the server. If a client
     * sends the "exit" message, the server will disconnect the client and
     * exit.
     */
    public static void main(String[] args) {
        try {
            // Create a socket to connect to the server
            Socket socket = new Socket("localhost", PORT);
            System.out.println("Connected to server! PORT::" + socket.getPort());

            // Read a message from the server using a BufferedReader
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));

            // Thread to read messages from server
            Thread receiveThread = new Thread(() -> {
                try {
                    String message;
                    while (null != (message = in.readLine())) {
                        System.out.println("Server: " + message);
                        if (message.equalsIgnoreCase(EXIT_MESSAGE)) {
                            System.out.println("Server disconnected.");
                            break;
                        }
                    }
                    socket.close();
                } catch (IOException e) {
                    System.out.println(ERROR + e.getMessage());
                }
            });

            // Thread to send messages to server
            Thread sendThread = new Thread(() -> {
                try {
                    String message;
                    while (true) {
                        message = consoleInput.readLine();
                        output.println(message);
                        if (message.equalsIgnoreCase(EXIT_MESSAGE)) {
                            break;
                        }
                    }
                    socket.close();
                } catch (IOException e) {
                    System.out.println(ERROR + e.getMessage());
                }
            });

            receiveThread.start();
            sendThread.start();
        } catch (IOException e) {
            System.out.println(ERROR + e.getMessage());
        }
    }
}
