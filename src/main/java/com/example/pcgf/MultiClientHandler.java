package com.example.pcgf;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class MultiClientHandler extends Thread {
    private Socket socket = new Socket();
    private ObjectInputStream ois = null;
    private ObjectOutputStream oos = null;
    private ObjectInputStream ois2 = null;
    private ObjectOutputStream oos2 = null;
    private static Map<String, ObjectInputStream> allReader = new HashMap<>();
    private static Map<String, ObjectOutputStream> allWriter = new HashMap<>();
    public String key;

    // constructor
    public MultiClientHandler(String key, Socket s, ObjectInputStream ois, ObjectOutputStream oos) {
        this.socket = s;
        this.key = key;
        this.ois = ois;
        this.oos = oos;
        allReader.put(key, ois);
        allWriter.put(key, oos);
    }

    public void run() {
        String received = "";
        boolean runi = true;
        while (runi) {
            if (allReader.keySet().size() == 2) {
                for (String string : allReader.keySet()) {
                    if(!key.equals(string)){
                        ois2 = allReader.get(string);
                        oos2 = allWriter.get(string);//2 . Write
                    }else{
                        ois = allReader.get(string);//1 . Read
                        oos = allWriter.get(string);
                    }
                }
                runi = false;
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int countLoop = 0;

        while (true) {
            try {
                if(countLoop%2==0){
                    received = (String) ois.readObject();
                    oos2.writeObject(received);
                }else{
                    received = (String) ois2.readObject();
                    oos.writeObject(received);
                }

                System.out.println("Response of client " + key + ": " + received);
            } catch (Exception e) {
                e.printStackTrace();
            }
            countLoop++;

        }

    }
}
