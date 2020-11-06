package lesson6;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static Socket clientSocket;
    private static final int PORT = 8190;
    private static final String IP_ADDRESS = "localhost";

    private static DataInputStream systemIn;
    private static DataOutput systemOut;
    private static Scanner in;

    public static void main(String[] args) {

        try {
            clientSocket = new Socket(IP_ADDRESS, PORT);
            systemIn = new DataInputStream(clientSocket.getInputStream());
            systemOut = new DataOutputStream(clientSocket.getOutputStream());
            in = new Scanner(System.in);

            new Thread(() -> {
                try {
                    while (true) {
                        String serverStr = systemIn.readUTF();
                        if (serverStr.equals("/end")) {
                            systemOut.writeUTF("/end");
                            break;
                        }
                        System.out.println(serverStr);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            new Thread(() -> {
                try {
                    while (true) {
                        String userStr = in.nextLine();
                        if (userStr.equals("/end")) {
                            systemOut.writeUTF("/end");
                            break;
                        }
                        systemOut.writeUTF("User: " + userStr);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                        System.out.println("User Disconnected");
                    try {
                        clientSocket.close();
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
