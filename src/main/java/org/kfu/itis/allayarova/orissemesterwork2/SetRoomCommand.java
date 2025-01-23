package org.kfu.itis.allayarova.orissemesterwork2;

import org.kfu.itis.allayarova.orissemesterwork2.client.Room;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SetRoomCommand implements Command{
    private static Map<Integer, Room> rooms;
    public SetRoomCommand() {
        rooms = Server.getRooms();
    }

    @Override
    public String execute(String value, ClientHandler clientHandler) {
        Room room = rooms.get(Integer.parseInt(value.trim()));
        clientHandler.setRoom(room);
        room.getClients().add(clientHandler);
        return "01";
    }
}
