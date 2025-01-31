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
        if(nextClientHandler!=null && c.equals(Commands.NOTIFY_NEXT)){
            nextClientHandler.sendMessage(Commands.PUT_CARD_ON_TABLE.getCode()+":"+nextCardId);
            return Commands.DO_NOTHING.getCode()+":"+1;
        }else if(c.equals(Commands.SELECT_ROW_TO_PICK)){
            return c.getCode()+":"+1;
        }else if(c.equals(Commands.ROUND_COMPLETED)){
            room.sendPenaltyPoints(Commands.ROUND_COMPLETED.getCode()+":");
            if(gameState.getMoveCounter()==10){
                gameState.setMoveCounter(0);
                room.broadcastMessage(Commands.GET_CARDS.getCode()+":"+-1);
            }
        }
        return Commands.ROUND_COMPLETED.getCode()+":"+player.getPenaltyPoints();
    }
}
