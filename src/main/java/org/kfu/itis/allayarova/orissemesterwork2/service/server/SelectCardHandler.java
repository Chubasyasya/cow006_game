package org.kfu.itis.allayarova.orissemesterwork2.service.server;

import org.kfu.itis.allayarova.orissemesterwork2.ClientHandler;
import org.kfu.itis.allayarova.orissemesterwork2.models.GameState;
import org.kfu.itis.allayarova.orissemesterwork2.models.Room;
import org.kfu.itis.allayarova.orissemesterwork2.service.Commands;
import org.kfu.itis.allayarova.orissemesterwork2.service.server.CommandHandler;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SelectCardHandler implements CommandHandler<String> {

    @Override
    public String handle(ClientHandler clientHandler, List<String> value) {
        Room room = clientHandler.getRoom();
        GameState gameState = room.getGameState();
        int cardId = Integer.parseInt(value.get(0));
        System.out.println("Сервер получил карту "+cardId);

        gameState.addClientAndSelectedCards(clientHandler, cardId, room.getClients().size());
        System.out.println("Количество игроков сдавших карты "+room.getClients().size());

        if (!gameState.allPlayersSelectedCards(room.getClients().size())) {
            return Commands.DO_NOTHING.getCode() + ":" + 1;
        }else {
            gameState.updateMoveCounter();
            gameState.rangingCards();
            ClientHandler nextClientHandler = gameState.getNextClientHandlerByCurrentCardId(0);
            if (nextClientHandler != null) {
                System.out.println("Отправляю класть карты");
                nextClientHandler.sendMessage(Commands.PUT_CARD_ON_TABLE.getCode() + ":" + gameState.getNextCardId(0));
            }
        }
        return Commands.DO_NOTHING.getCode() + ":" + 1;
    }

}
