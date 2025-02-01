package org.kfu.itis.allayarova.orissemesterwork2.server.serverHandlers;

import org.kfu.itis.allayarova.orissemesterwork2.server.ClientHandler;
import org.kfu.itis.allayarova.orissemesterwork2.service.Commands;

import java.util.Collections;
import java.util.List;

public class StartGameHandler implements CommandHandler<String>{
    @Override
    public String handle(ClientHandler clientHandler, List<String> value) {
        CommandHandler fieldInitHandler = CommandHandlerFactory.getHandler(Commands.FIELD_INIT);
        String fieldInitMessage = fieldInitHandler.handle(clientHandler, Collections.singletonList(4));

        CommandHandler getCardsHandler = CommandHandlerFactory.getHandler(Commands.GET_CARDS);
        for (ClientHandler client: clientHandler.getRoom().getClients()) {
            if(client!=clientHandler) {
                String getCardsMessage = getCardsHandler.handle(clientHandler, Collections.singletonList("10"));
                String resultMessage = getCardsMessage+";"+fieldInitMessage;
                System.out.println("Handler send this message in game start" + resultMessage);
                client.sendMessage(resultMessage);
            }
        }


        return getCardsHandler.handle(clientHandler, Collections.singletonList("10"))+";"+fieldInitMessage;
    }
}
