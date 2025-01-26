package org.kfu.itis.allayarova.orissemesterwork2;

import org.kfu.itis.allayarova.orissemesterwork2.models.Room;
import org.kfu.itis.allayarova.orissemesterwork2.service.Commands;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {
    private static Map<Integer, Room> rooms = new HashMap<>();

    public static void main(String[] args){
        rooms.put(1, new Room(2, new HashSet<>()));
        rooms.put(2, new Room(3, new HashSet<>()));

        try(ServerSocket serverSocket = new ServerSocket(1234)){
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client with ip address: " + socket.getInetAddress());

                sendRoomList(socket);

                ClientHandler clientHandler = new ClientHandler(socket);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void sendRoomList(Socket socket) throws IOException {
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        StringBuilder roomList = new StringBuilder();
        for (Integer roomNumber : rooms.keySet()) {
            roomList.append(roomNumber).append(" ");
        }
        if (roomList.length() > 0) {
            roomList.setLength(roomList.length() - 1);
        }

        out.write(Commands.SEND_ROOM_LIST.getCode() +":" + roomList + "\n");
        out.flush();
    }

    public static Map<Integer, Room> getRooms() {
        return rooms;
    }
}
