package org.kfu.itis.allayarova.orissemesterwork2.server.serverHandlers;

import org.kfu.itis.allayarova.orissemesterwork2.server.ClientHandler;
import org.kfu.itis.allayarova.orissemesterwork2.models.Card;
import org.kfu.itis.allayarova.orissemesterwork2.server.GameState;
import org.kfu.itis.allayarova.orissemesterwork2.models.Player;
import org.kfu.itis.allayarova.orissemesterwork2.server.Room;
import org.kfu.itis.allayarova.orissemesterwork2.service.Commands;

import java.util.List;

public class SelectRowToPickHandler implements CommandHandler<String> {
    @Override
    public String handle(ClientHandler clientHandler, List<String> value) {
        Room room = clientHandler.getRoom();
        Player player = clientHandler.getPlayer();
        GameState gameState = room.getGameState();

        gameState.pickRowCards(Integer.parseInt(value.getFirst()), clientHandler);
        Card card = gameState.getCardByClientHandler(clientHandler);
        room.broadcastMessage(Commands.UPDATE_PLAYING_FIELD.getCode()+ ":" +gameState.getPlayingFieldString());

        ClientHandler nextClientHandler = gameState.getNextClientHandlerByCurrentCardId(card.getNumber());
        if(nextClientHandler!=null) {
            nextClientHandler.sendMessage(Commands.PUT_CARD_ON_TABLE.getCode() + ":" + gameState.getNextCardId(card.getNumber()));
        }
        return Commands.ROUND_COMPLETED.getCode()+":"+player.getPenaltyPoints();
    }
}
