package com.study.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient {

    private static final String EXIT_MESSAGE = "exit";
    private static final String ERROR = "Error: ";
    private static final String SERVER_ADDRESS = "localhost";
    private static final int PORT = 2002;

    private static Thread sendThread;

    /**
     * The main method of the UDPClient class. It creates a DatagramSocket to
     * connect to the server and initializes the server address. Two threads are
     * created: one for receiving messages from the server and one for sending
     * messages to the server. If the received or input message is "exit", the
     * client disconnects and the application exits. The client reads messages from
     * the console and sends them to the server, while also printing server messages
     * to the console.
     */
    public static void main(String[] args) {
        try {
            // Create a socket to connect to the server
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName(SERVER_ADDRESS);
            System.out.println("UDP Client is running on PORT::" + clientSocket.getLocalPort());

            // Read a message from the server using a BufferedReader
            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));

            // Thread to read messages from server
            Thread receiveThread = new Thread(() -> {
                try {
                    while (true) {
                        byte[] receiveData = new byte[1024];
                        // Create a packet to receive data
                        DatagramPacket packet = new DatagramPacket(receiveData, receiveData.length);
                        clientSocket.receive(packet);

                        String message = new String(packet.getData(), 0, packet.getLength());

                        if (message.equalsIgnoreCase(EXIT_MESSAGE)) {
                            System.out.println("Server disconnected.");
                            consoleInput.close();
                            sendThread.interrupt();
                            break;
                        }
                        System.out.println("Server: " + message);
                    }
                    clientSocket.close();
                } catch (IOException e) {
                    System.out.println(ERROR + e.getMessage());
                }
            });

            // Thread to send messages to server
            sendThread = new Thread(() -> {
                try {
                    while (true) {
                        String message = consoleInput.readLine();
                        byte[] sendData = message.getBytes();
                        // Create a packet to send data
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, PORT);
                        clientSocket.send(sendPacket);
                        if (message.equalsIgnoreCase(EXIT_MESSAGE)) {
                            consoleInput.close();
                            receiveThread.interrupt();
                            break;
                        }
                    }
                    clientSocket.close();
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
