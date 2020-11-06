package lesson6;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static final int PORT = 8190;

    private static DataInputStream systemIn;
    private static DataOutput systemOut;
    private static Scanner in;

    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started");

            clientSocket = serverSocket.accept();
            System.out.println("Client connected");

            systemIn = new DataInputStream(clientSocket.getInputStream());
            systemOut = new DataOutputStream(clientSocket.getOutputStream());
            in = new Scanner(System.in);

            new Thread(() -> {
                try {
                    while (true) {
                        String userStr = systemIn.readUTF();

                        if (userStr.equals("/end")) {
                            systemOut.writeUTF("/end");
                            break;
                        }
                        System.out.println(userStr);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            new Thread(() -> {
                try {
                    while (true) {
                        String serverStr = in.nextLine();
                        if (serverStr.equals("/end")) {
                            systemOut.writeUTF("/end");
                            break;
                        }
                        systemOut.writeUTF("Server: " + serverStr);
                    }
                } catch (IOException e) {
                    e.printStackTrace();

                } finally {
                        System.out.println("Server disconnected");
                    try {
                        serverSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
