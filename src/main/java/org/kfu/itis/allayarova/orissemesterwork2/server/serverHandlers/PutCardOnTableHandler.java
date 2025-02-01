package org.kfu.itis.allayarova.orissemesterwork2.server.serverHandlers;

import org.kfu.itis.allayarova.orissemesterwork2.server.ClientHandler;
import org.kfu.itis.allayarova.orissemesterwork2.server.GameState;
import org.kfu.itis.allayarova.orissemesterwork2.models.Player;
import org.kfu.itis.allayarova.orissemesterwork2.server.Room;
import org.kfu.itis.allayarova.orissemesterwork2.service.Commands;

import java.util.List;

public class PutCardOnTableHandler implements CommandHandler<String>{
    private static int MAX_PENALTY_POINTS = 22;
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
                gameState.sortPlayers();
                if(gameState.getPlayersRangedByPoints().getLast().getPenaltyPoints()>=MAX_PENALTY_POINTS) {
                    List<ClientHandler> losers = gameState.determineLosers();
                    List<ClientHandler> winners = gameState.determineWinners();
                    List<ClientHandler> avgPlayers = gameState.determineAvgPlayers();

                    if (losers.getFirst().getPlayer().getPenaltyPoints() >= MAX_PENALTY_POINTS) {
                        room.sendMessage(losers, Commands.GAME_RESULT.getCode() + ":" + 0);
                        room.sendMessage(winners, Commands.GAME_RESULT.getCode() + ":" + 1);
                        room.sendMessage(avgPlayers, Commands.GAME_RESULT.getCode() + ":" + 2);
                    }
                    room.getClients().clear();
                }

                gameState.updateGameState();
                StartGameHandler nextHandler = (StartGameHandler) CommandHandlerFactory.getHandler(Commands.START_GAME);
                return nextHandler.handle(clientHandler, null);
            }
        }
        return Commands.ROUND_COMPLETED.getCode()+":"+player.getPenaltyPoints();
    }
}
