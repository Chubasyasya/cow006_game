package org.kfu.itis.allayarova.orissemesterwork2.service.server;

import org.kfu.itis.allayarova.orissemesterwork2.ClientHandler;
import org.kfu.itis.allayarova.orissemesterwork2.models.GameState;
import org.kfu.itis.allayarova.orissemesterwork2.models.Player;
import org.kfu.itis.allayarova.orissemesterwork2.models.Room;
import org.kfu.itis.allayarova.orissemesterwork2.service.Commands;

import java.util.List;

public class PutCardOnTableHandler implements CommandHandler<String>{
    @Override
    public String handle(ClientHandler clientHandler, List<String> value) {
        GameState gameState = clientHandler.getRoom().getGameState();
        int cardId = Integer.parseInt(value.getFirst());
        Player player = clientHandler.getPlayer();
        Commands c = gameState.putCardOnTable(cardId, player);
        Room room = clientHandler.getRoom();
        room.broadcastMessage(Commands.UPDATE_PLAYING_FIELD.getCode()+ ":" +gameState.getPlayingFieldString());

        ClientHandler nextClientHandler = gameState.getNextClientHandlerByCurrentCardId(cardId);
        int nextCardId = gameState.getNextCardId(cardId);
        if(c.equals(Commands.NOTIFY_NEXT)){
            nextClientHandler.sendMessage(Commands.PUT_CARD_ON_TABLE.getCode()+":"+nextCardId);
        }else if(c.equals(Commands.SELECT_ROW_TO_PICK)){
            return c.getCode()+":"+nextCardId;
        }else if(c.equals(Commands.ROUND_COMPLETED)){
            room.broadcastMessage(Commands.ROUND_COMPLETED.getCode()+ ":" + player.getPenaltyPoints());
        }
        return c.getCode()+":"+1;
    }
}
