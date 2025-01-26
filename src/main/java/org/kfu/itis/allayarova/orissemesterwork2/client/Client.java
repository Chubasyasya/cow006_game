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

    public Client() {
        try {
            socket = new Socket(HOST, PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException("Failed to connect to server: " + e.getMessage(), e);
        }
    }

    public void listenMessages(MenuNet menuNet) {
        new Thread(() -> {
            addListener(menuNet);
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    dispatcher.dispatch(new NetworkEvent("response", message));
                    System.out.println("Message received: " + message);
                }
            } catch (IOException e) {
                System.err.println("Error while reading messages: " + e.getMessage());
            } finally {
                closeResources();
            }
        }).start();
    }

    public void sendMessage(String message) {
        synchronized (out) {
            out.println(message);
            out.flush();
        }
    }

    private void closeResources() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            System.err.println("Failed to close resources: " + e.getMessage());
        }
    }
}
