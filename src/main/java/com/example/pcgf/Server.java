package com.example.pcgf;

import java.io.*;
import java.net.*;
public class Server
{
    private static ServerSocket serverSocket;
    private static  ObjectOutputStream streamToClient;
    private static ObjectInputStream streamFromClient;
    private static Socket fromClient;
    private static int count = 0;

    public static void main(String[] args)
    {
        try {
            serverSocket = new ServerSocket(7543);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(true){
            try {
                fromClient = serverSocket.accept();
                /*System.out.println("Client connection number "+count);
                count++;*/
            } catch (IOException e) {
                e.printStackTrace();
            }
            r();
        }

    }

    private static void r(){
        Thread thread = new Thread(){
            public void run(){

                try{
                    while(true)
                    {
                        streamFromClient = new ObjectInputStream(fromClient.getInputStream());
                        streamToClient = new ObjectOutputStream(fromClient.getOutputStream());
                        try{
                            System.out.println("--------------");
                            System.out.println((String) streamFromClient.readObject());
                            streamToClient.writeObject(streamFromClient.readObject());
                        }catch (Exception e){
                            System.out.println("NULL");
                        }
                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
}
