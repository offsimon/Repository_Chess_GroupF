package com.example.pcgf;

import javafx.scene.layout.GridPane;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class Server implements Serializable {
    private static ServerSocket serverSocket;
    private static Socket fromClient;
    private static int count = 0;
    private static final Map<String, ObjectInputStream> allReader = new HashMap<>();
    private static final Map<String, ObjectOutputStream> allWriter = new HashMap<>();
    private static String gegner = "";

    public static void main(String[] args) {

        try {
            serverSocket = new ServerSocket(4711);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int num = 1;
        while (true) {
            fromClient = null;
            try {
                fromClient = serverSocket.accept();
                String key = num+"";
                System.out.println("A new client is connected: "+fromClient);
                ObjectInputStream ois = new ObjectInputStream(fromClient.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(fromClient.getOutputStream());

                Thread t = new MultiClientHandler(key, fromClient, ois, oos);
                t.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            num++;
        }

    }
}
