package org.kfu.itis.allayarova.orissemesterwork2.service.server;

import org.kfu.itis.allayarova.orissemesterwork2.ClientHandler;
import org.kfu.itis.allayarova.orissemesterwork2.service.Commands;
import org.kfu.itis.allayarova.orissemesterwork2.service.server.CommandHandler;

public class EnterInRoomHandler implements CommandHandler {

    @Override
    public String handle(ClientHandler clientHandler) {
        return Commands.ENTER_IN_ROOM + ":" + 1;
    }
}
