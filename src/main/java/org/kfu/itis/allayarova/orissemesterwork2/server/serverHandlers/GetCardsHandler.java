package org.kfu.itis.allayarova.orissemesterwork2.server.serverHandlers;

import org.kfu.itis.allayarova.orissemesterwork2.server.ClientHandler;
import org.kfu.itis.allayarova.orissemesterwork2.service.Commands;

import java.util.List;

public class GetCardsHandler implements CommandHandler<String>{
    @Override
    public String handle(ClientHandler clientHandler, List<String> value) {
        StringBuilder builder = new StringBuilder();
        builder.append(Commands.GET_CARDS.getCode()).append(":");
        String f = value.getFirst();
        int size = Integer.parseInt(f);

        for(int i = 0; i < size; i++) {
            builder.append(clientHandler.getRoom().getGameState().getCard().getNumber()).append(" ");
        }
        return builder.toString();
    }
}
