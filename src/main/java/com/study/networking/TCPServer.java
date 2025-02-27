package com.study.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

    private static final int PORT = 2001;
    private static final String EXIT_MESSAGE = "exit";
    private static final String ERROR = "Error: ";

    /**
     * This is the main method of the TCPServer class. It creates a server socket
     * and waits for a client to connect. Once a client connects, it creates two
     * threads. The first thread reads messages from the client and prints them to
     * the console. The second thread reads messages from the console and sends
     * them to the client. If a client sends the "exit" message, the server will
     * disconnect the client and exit.
     */
    public static void main(String[] args) {

        // Create a server socket
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server started! Waiting for connection...");

            // Wait for a client to connect
            // accept() will block until a client connects
            Socket socket = serverSocket.accept();
            System.out.println("Client connected! PORT::" + socket.getLocalPort());

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));

            // Thread to read messages from Client
            Thread receiveThread = new Thread(() -> {
                try {
                    String message;
                    while ((message = in.readLine()) != null) {
                        System.out.println("Client: " + message);
                        if (message.equalsIgnoreCase(EXIT_MESSAGE)) {
                            System.out.println("Client disconnected!");
                            break;
                        }
                    }
                    socket.close();
                    serverSocket.close();
                } catch (IOException e) {
                    System.out.println(ERROR + e.getMessage());
                }
            });

            // Thread to write messages to Client
            Thread sendThread = new Thread(() -> {
                try {
                    String message;
                    while (true) {
                        message = consoleInput.readLine();
                        out.println(message);
                        if (message.equalsIgnoreCase(EXIT_MESSAGE)) {
                            break;
                        }
                    }
                    socket.close();
                    serverSocket.close();
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
