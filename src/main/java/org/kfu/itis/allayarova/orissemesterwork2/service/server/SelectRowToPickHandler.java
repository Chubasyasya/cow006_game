package org.kfu.itis.allayarova.orissemesterwork2.service.server;

import org.kfu.itis.allayarova.orissemesterwork2.ClientHandler;
import org.kfu.itis.allayarova.orissemesterwork2.models.Card;
import org.kfu.itis.allayarova.orissemesterwork2.models.GameState;
import org.kfu.itis.allayarova.orissemesterwork2.models.Room;
import org.kfu.itis.allayarova.orissemesterwork2.service.Commands;

import java.util.List;

public class SelectRowToPickHandler implements CommandHandler<String> {
    @Override
    public String handle(ClientHandler clientHandler, List<String> value) {
        Room room = clientHandler.getRoom();
        GameState gameState = room.getGameState();

        gameState.pickRowCards(Integer.parseInt(value.getFirst()), clientHandler);
        Card card = gameState.getCardByClientHandler(clientHandler);
        room.broadcastMessage(Commands.UPDATE_PLAYING_FIELD.getCode()+ ":" +gameState.getPlayingFieldString());

        ClientHandler nextClientHandler = gameState.getNextClientHandlerByCurrentCardId(card.getNumber());
        nextClientHandler.sendMessage(Commands.PUT_CARD_ON_TABLE.getCode()+":"+gameState.getNextCardId(card.getNumber()));
        return Commands.DO_NOTHING.getCode() + ":" + 1;
    }
}
