package org.kfu.itis.allayarova.orissemesterwork2.service;

import org.kfu.itis.allayarova.orissemesterwork2.client.Client;
import org.kfu.itis.allayarova.orissemesterwork2.models.Action;
import org.kfu.itis.allayarova.orissemesterwork2.service.server.messageListener.EventListener;
import org.kfu.itis.allayarova.orissemesterwork2.service.server.messageListener.NetworkEvent;

import java.util.List;

public class GameNet implements EventListener {
    private Game game;
    private Client client;

    public GameNet(Game game, Client client) {
        this.game = game;
        this.client = client;
        client.addListener(this);
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
        System.out.println("Событие обработано gameNet: " + event.getData());
        List<Action> actions = CommandConverter.toMessage(event.getData());
        for(Action action: actions) {
            int commandCode = action.getCommand().getCode();
            if (commandCode == Commands.START_GAME.getCode()) {
                game.beginGame();
            } else if (commandCode == Commands.GET_CARDS.getCode()) {
                game.getCards(action.getValue());
            }else if (commandCode == Commands.FIELD_INIT.getCode()){
                game.fieldInit(action.getValue());
            }else if(commandCode == Commands.PUT_CARD_ON_TABLE.getCode()){
                client.sendMessage(event.getData());
            }else if(commandCode == Commands.SELECT_ROW_TO_PICK.getCode()){
                game.selectRowToPick();
            }else if(commandCode == Commands.UPDATE_PLAYING_FIELD.getCode()){
                game.updatePlayingField(action.getValue());
            }else if(commandCode == Commands.ROUND_COMPLETED.getCode()){
                game.roundCompleted(Integer.parseInt((String) action.getValue().getFirst()));
            }
        }
    }

}
