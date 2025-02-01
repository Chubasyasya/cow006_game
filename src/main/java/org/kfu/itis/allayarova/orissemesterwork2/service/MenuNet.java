package org.kfu.itis.allayarova.orissemesterwork2.service;

import org.kfu.itis.allayarova.orissemesterwork2.client.Client;
import org.kfu.itis.allayarova.orissemesterwork2.models.Action;
import org.kfu.itis.allayarova.orissemesterwork2.client.messageListener.EventListener;
import org.kfu.itis.allayarova.orissemesterwork2.models.Message;

import java.util.List;
import java.util.stream.Collectors;

public class MenuNet implements EventListener{
    protected Client client;
    private Menu menu;


    public MenuNet(Menu menu) {
        this.menu = menu;

        client = new Client();
        client.addListener(this);
        client.listenMessages();
    }

    public void send(Action action){
        client.sendMessage(CommandConverter.actionToMessage(action));
    }

    @Override
    public void onEvent(Message message) {
        Action<String> action = CommandConverter.messageToAction(message);

//        for(Action action: actions) {
            if (action.getCommand().getCode() == Commands.ENTER_IN_ROOM.getCode()) {
                menu.enterInRoomResponse(Integer.parseInt((String) action.getValue().getFirst()), Integer.parseInt((String) action.getValue().get(1)));
            } else if (action.getCommand().getCode() == Commands.SEND_ROOM_LIST.getCode()) {
                menu.createButtons(((List<String>) action.getValue())
                        .stream()
                        .map(Integer::parseInt)
                        .collect(Collectors.toList()));
            }
//        }
    }

    public Client getClient() {
        return client;
    }
}
