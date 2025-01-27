package org.kfu.itis.allayarova.orissemesterwork2.service;

import org.kfu.itis.allayarova.orissemesterwork2.client.Client;
import org.kfu.itis.allayarova.orissemesterwork2.models.Action;
import org.kfu.itis.allayarova.orissemesterwork2.service.server.messageListener.EventListener;
import org.kfu.itis.allayarova.orissemesterwork2.service.server.messageListener.NetworkEvent;

public class GameNet implements EventListener {
    private Game game;
    private Client client;

    public GameNet(Game game, Client client) {
        this.game = game;
        this.client = client;
    }

    public void startGame(Action action) {
        client.addListener(this);
        send(action);

    }

    public void send(Action action){
        client.sendMessage(CommandConverter.toString(action));
    }

    @Override
    public void onEvent(NetworkEvent event) {
        if ("response".equals(event.getType())) {
            eventReact(event);
        }
    }

    public void eventReact(NetworkEvent event){
        System.out.println("Событие обработано: " + event.getData());
        Action action = CommandConverter.toMessage(event.getData());
        if(action.getCommand().getCode()==Commands.START_GAME.getCode()){
            game.beginGame();
        } else if (action.getCommand().getCode()==Commands.GET_CARDS.getCode()) {
            game.getCards(action.getValue());
        }
    }
}
