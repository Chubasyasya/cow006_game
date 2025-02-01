package org.kfu.itis.allayarova.orissemesterwork2.server.serverHandlers;

import org.kfu.itis.allayarova.orissemesterwork2.server.ClientHandler;
import org.kfu.itis.allayarova.orissemesterwork2.models.Card;
import org.kfu.itis.allayarova.orissemesterwork2.server.GameState;
import org.kfu.itis.allayarova.orissemesterwork2.service.Commands;

import java.util.List;

public class FieldInitHandler implements CommandHandler<Integer>{
    @Override
    public String handle(ClientHandler clientHandler, List<Integer> value) {
        StringBuilder builder = new StringBuilder();
        builder.append(Commands.FIELD_INIT.getCode()).append(":");

        int size = value.getFirst();
        GameState gameState = clientHandler.getRoom().getGameState();
        gameState.playingFieldInit();

        for(int i = 0; i < size; i++) {
            Card card = gameState.getCard();
            builder.append(card.getNumber()).append(" ");
            gameState.setCardOnPlayingField(i, 0, card);
            gameState.setLastInRows(i, card);
        }
        return builder.toString();
    }
}
