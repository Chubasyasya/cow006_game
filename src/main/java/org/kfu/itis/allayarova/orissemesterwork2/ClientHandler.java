package org.kfu.itis.allayarova.orissemesterwork2;

import org.kfu.itis.allayarova.orissemesterwork2.models.Room;
import org.kfu.itis.allayarova.orissemesterwork2.models.Action;
import org.kfu.itis.allayarova.orissemesterwork2.models.Player;
import org.kfu.itis.allayarova.orissemesterwork2.service.CommandConverter;
import org.kfu.itis.allayarova.orissemesterwork2.service.server.CommandHandler;
import org.kfu.itis.allayarova.orissemesterwork2.service.server.CommandHandlerFactory;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
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
            throw new RuntimeException("Failed to initialize client handler: " + e.getMessage(), e);
        }
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                Action action = CommandConverter.toMessage(message);
                CommandHandler handler = CommandHandlerFactory.getHandler(action.getCommand());
                if (handler != null) {
                    String serverMessage = handler.handle(this, action.getValue());
                    sendMessage(serverMessage);
                    System.out.println("Message sent to client: " + serverMessage);
                }
            }
        } catch (IOException e) {
            System.err.println("Connection error: " + e.getMessage());
        } finally {
            closeResources();
        }
    }

    public synchronized void sendMessage(String message) {
        out.println(message);
        out.flush();
    }

    public void sendingForRoom(String message) {
        for (ClientHandler client : room.getClients()) {
            if (client != this) {
                try {
                    client.sendMessage(message);
                } catch (Exception e) {
                    System.err.println("Failed to send message to client: " + e.getMessage());
                }
            }
        }
    }

    public Room getRoom() {
        return room;
    }

    public boolean setRoomById(int roomId) {
        room = Server.getRooms().get(roomId);
        if (room != null) {
            return room.addClient(this);
        }
        return false;
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

