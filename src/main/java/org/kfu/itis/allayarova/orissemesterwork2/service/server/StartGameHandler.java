package org.kfu.itis.allayarova.orissemesterwork2.service.server;

import org.kfu.itis.allayarova.orissemesterwork2.ClientHandler;
import org.kfu.itis.allayarova.orissemesterwork2.service.Commands;

import java.util.List;

public class StartGameHandler implements CommandHandler<String>{
    @Override
    public String handle(ClientHandler clientHandler, List<String> value) {
        String startGameCommand = Commands.START_GAME.getCode()+":"+1;
        clientHandler.sendingForRoom(startGameCommand);
        return startGameCommand;
    }
}
