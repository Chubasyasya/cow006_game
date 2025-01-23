package org.kfu.itis.allayarova.orissemesterwork2;

import org.kfu.itis.allayarova.orissemesterwork2.client.Room;
import org.kfu.itis.allayarova.orissemesterwork2.models.Player;

import java.io.*;
import java.net.Socket;
import java.util.Map;
import java.util.Set;

public class ClientHandler implements Runnable{
    private Socket socket;

    private Thread thread;
    private Room room;
    private Player player;
    private PrintWriter out;
    private BufferedReader in;


    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            this.player = new Player();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))){
            String message;
            while((message = br.readLine()) != null){
                String serverMessage = CommandsResolver.executeCommand(message, this);
                out.write(serverMessage);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendingForRoom(String message) {
        for (ClientHandler c : room.getClients()) {
            if (c != this){
                c.out.println(message);
            }
        }
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
