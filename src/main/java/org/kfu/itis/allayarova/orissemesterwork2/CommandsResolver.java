package org.kfu.itis.allayarova.orissemesterwork2;

import java.util.HashMap;
import java.util.Map;

public class CommandsResolver {
    private static final Map<Integer, Command> commands;

    static {
        commands = new HashMap<>();
        commands.put(0, new SetRoomCommand());

    }

    public static String executeCommand(String message, ClientHandler clientHandler) {
        String[] commandArr = message.split(":");
        Command command = commands.get(commandArr[0]);
        if (command != null) {
            return command.execute(commandArr[1], clientHandler);
        }
        return null;
    }
}
