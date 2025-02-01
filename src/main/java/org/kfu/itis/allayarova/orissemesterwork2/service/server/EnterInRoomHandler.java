package org.kfu.itis.allayarova.orissemesterwork2.service.server;

import org.kfu.itis.allayarova.orissemesterwork2.ClientHandler;
import org.kfu.itis.allayarova.orissemesterwork2.models.GameState;
import org.kfu.itis.allayarova.orissemesterwork2.models.Room;
import org.kfu.itis.allayarova.orissemesterwork2.service.Commands;

import java.util.List;

public class EnterInRoomHandler implements CommandHandler<String> {

    @Override
    public String handle(ClientHandler clientHandler, List<String> value) {
        int roomId = Integer.parseInt(value.getFirst());
        boolean enterInRoom = clientHandler.setRoomById(roomId);

        Room room =  clientHandler.getRoom();
        int beginGameFlag = 0;
        if(room.getSize() == room.getClients().size()){
            beginGameFlag = 1;
            room.setGameState(new GameState(room));
            room.getGameState().setPlayersRangedByPoints(room.getClients());
        }
        int result = enterInRoom ? roomId : -1;
        return Commands.ENTER_IN_ROOM.getCode() + ":" + result+" "+beginGameFlag;
    }
}
