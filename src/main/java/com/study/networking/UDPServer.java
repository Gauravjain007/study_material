package com.study.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;

public class UDPServer {

    private static final int PORT = 2002;
    private static final String EXIT_MESSAGE = "exit";
    private static final String ERROR = "Error: ";
    private static SocketAddress clientAddress;

    private static Thread sendThread;

    /**
     * This is the main method of the UDPServer class. It creates a DatagramSocket
     * and waits for a client to connect. Once a client connects, it creates two
     * threads. The first thread reads messages from the client and prints them to
     * the console. The second thread reads messages from the console and sends
     * them to the client. If a client sends the "exit" message, the server will
     * disconnect the client and exit.
     */
    public static void main(String[] args) {
        try {
            // Create a server socket
            DatagramSocket serverSocket = new DatagramSocket(PORT);
            System.out.println("UDP Server is running on PORT::" + serverSocket.getLocalPort());

            // Read a message from the client using a BufferedReader
            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));

            // Thread to read messages from Client
            Thread receiveThread = new Thread(() -> {
                try {
                    while (true) {
                        byte[] receiveData = new byte[1024];
                        DatagramPacket packet = new DatagramPacket(receiveData, receiveData.length);
                        serverSocket.receive(packet);

                        clientAddress = packet.getSocketAddress();

                        String message = new String(packet.getData(), 0, packet.getLength());
                        System.out.println("Client: " + message);

                        if (message.equalsIgnoreCase(EXIT_MESSAGE)) {
                            System.out.println("Client disconnected.");
                            consoleInput.close();
                            sendThread.interrupt();
                            break;
                        }
                    }
                } catch (IOException e) {
                    System.out.println(ERROR + e.getMessage());
                }
            });

            // Thread to send messages to Client
            sendThread = new Thread(() -> {
                try {
                    while (true) {
                        String message = consoleInput.readLine();
                        if (null == clientAddress) {
                            System.out.println("No client connected.");
                        } else {
                            byte[] sendData = message.getBytes();
                            DatagramPacket packet = new DatagramPacket(sendData, sendData.length, clientAddress);
                            serverSocket.send(packet);
                        }
                        if (message.equalsIgnoreCase(EXIT_MESSAGE)) {
                            consoleInput.close();
                            receiveThread.interrupt();
                            break;
                        }
                    }
                    serverSocket.close();
                } catch (IOException e) {
                    System.out.println(ERROR + e.getMessage());
                }
            });

            // Start the threads
            receiveThread.start();
            sendThread.start();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
