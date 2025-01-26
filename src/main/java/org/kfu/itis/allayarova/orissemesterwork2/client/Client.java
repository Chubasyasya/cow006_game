package org.kfu.itis.allayarova.orissemesterwork2.client;

import org.kfu.itis.allayarova.orissemesterwork2.service.*;
import org.kfu.itis.allayarova.orissemesterwork2.service.server.messageListener.EventDispatcher;
import org.kfu.itis.allayarova.orissemesterwork2.service.server.messageListener.EventListener;
import org.kfu.itis.allayarova.orissemesterwork2.service.server.messageListener.NetworkEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private final static int PORT = 1234;
    private final static String HOST = "localhost";
    private Socket socket;
    protected BufferedReader in;
    protected PrintWriter out;
    private final EventDispatcher dispatcher = new EventDispatcher();

    public void addListener(EventListener listener) {
        dispatcher.addListener(listener);
    }


    public Client(){
        try {
            socket = new Socket(HOST, PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void listenMessages(MenuNet menuNet) {
        new Thread(() -> {
            addListener(menuNet);
            try {
                while(true){
                    String message;
                    while ((message = in.readLine()) != null) {
                       dispatcher.dispatch(new NetworkEvent("response", message));
                        System.out.println("Message received: " + message);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void sendMessage(String message){
        out.println(message);
    }
}
