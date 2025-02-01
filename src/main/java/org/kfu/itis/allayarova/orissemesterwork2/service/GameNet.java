package org.kfu.itis.allayarova.orissemesterwork2.service;

import org.kfu.itis.allayarova.orissemesterwork2.client.Client;
import org.kfu.itis.allayarova.orissemesterwork2.models.Action;
import org.kfu.itis.allayarova.orissemesterwork2.client.messageListener.EventListener;
import org.kfu.itis.allayarova.orissemesterwork2.client.messageListener.NetworkEvent;

import java.util.List;

public class GameNet implements EventListener {
    private Game game;
    private Client client;

    public GameNet(Game game, Client client) {
        this.game = game;
        this.client = client;
        client.addListener(this);
    }

    public void send(Action action){
        client.sendMessage(CommandConverter.toString(action));
    }

    @Override
    public void onEvent(NetworkEvent event) {
        System.out.println("Событие обработано gameNet: " + event.getData());
        List<Action<String>> actions = CommandConverter.toMessage(event.getData());
        for(Action<String> action: actions) {
            int commandCode = action.getCommand().getCode();
            if (commandCode == Commands.GET_CARDS.getCode()) {
                if(action.getValue().getFirst().equals("-1")){
                    client.sendMessage(Commands.GET_CARDS.getCode()+":"+10);
                }else {
                    game.getCards(action.getValue());
                }
            }else if (commandCode == Commands.FIELD_INIT.getCode()){
                game.fieldInit(action.getValue());
            }else if(commandCode == Commands.PUT_CARD_ON_TABLE.getCode()){
                client.sendMessage(event.getData());
            }else if(commandCode == Commands.SELECT_ROW_TO_PICK.getCode()){
                game.selectRowToPick();
            }else if(commandCode == Commands.UPDATE_PLAYING_FIELD.getCode()){
                game.updatePlayingField(action.getValue());
            }else if(commandCode == Commands.ROUND_COMPLETED.getCode()){
                game.roundCompleted(Integer.parseInt(action.getValue().getFirst()));
            }else if (commandCode == Commands.GAME_RESULT.getCode()){
                game.gameResult(Integer.parseInt(action.getValue().getFirst()));
            }
        }
    }

}
