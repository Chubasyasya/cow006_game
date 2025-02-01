package org.kfu.itis.allayarova.orissemesterwork2.client;

import org.kfu.itis.allayarova.orissemesterwork2.client.messageListener.EventDispatcher;
import org.kfu.itis.allayarova.orissemesterwork2.client.messageListener.EventListener;
import org.kfu.itis.allayarova.orissemesterwork2.models.Message;
import org.kfu.itis.allayarova.orissemesterwork2.service.CommandConverter;

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

    public void listenMessages() {
        new Thread(() -> {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    String[] strings = message.split(";");
                    for(String string: strings) {
                        for (EventListener d : dispatcher.getListeners()) {
                            System.out.println(d.toString());
                        }
                        System.out.println("Client broke string to message");
                        dispatcher.dispatch(CommandConverter.stringToMessage(string));
                        System.out.println("Message received: " + message);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error while reading messages: " + e.getMessage());
            } finally {
                closeResources();
            }
        }).start();
    }

    public void sendMessage(Message message) {
        String string= CommandConverter.messageToString(message);

        synchronized (out) {
            out.println(string);
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
