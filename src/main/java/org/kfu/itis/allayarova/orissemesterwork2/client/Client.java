package org.kfu.itis.allayarova.orissemesterwork2.client;

import javafx.application.Platform;
import org.kfu.itis.allayarova.orissemesterwork2.ClientHandler;
import org.kfu.itis.allayarova.orissemesterwork2.Game;
import org.kfu.itis.allayarova.orissemesterwork2.controllers.BaseController;
import org.kfu.itis.allayarova.orissemesterwork2.models.Player;

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


    public Client(){
        try {
            socket = new Socket(HOST, PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void listenMessages(Game game) {
        new Thread(() -> {
        try {
            while(true){
                String message;
                while ((message = in.readLine()) != null) {
                    message = message.trim();

                    game.handleMessage(message);
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
