package org.kfu.itis.allayarova.orissemesterwork2.service.server;

import org.kfu.itis.allayarova.orissemesterwork2.service.Commands;

import java.util.HashMap;
import java.util.Map;

public class CommandHandlerFactory {
    private static final Map<Commands, CommandHandler> commandHandlers = new HashMap<>();

    static {
        commandHandlers.put(Commands.ENTER_IN_ROOM, new EnterInRoomHandler());
        commandHandlers.put(Commands.START_GAME, new StartGameHandler());
        commandHandlers.put(Commands.GET_CARDS, new GetCardsHandler());
    }

    public static CommandHandler getHandler(Commands command) {
        return commandHandlers.get(command);
    }
}
