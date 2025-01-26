package org.kfu.itis.allayarova.orissemesterwork2.service;

import org.kfu.itis.allayarova.orissemesterwork2.client.Client;
import org.kfu.itis.allayarova.orissemesterwork2.models.Action;
import org.kfu.itis.allayarova.orissemesterwork2.service.server.messageListener.EventListener;
import org.kfu.itis.allayarova.orissemesterwork2.service.server.messageListener.NetworkEvent;

import java.util.List;
import java.util.stream.Collectors;

public class MenuNet implements EventListener {
    private Client client;
    private Menu menu;


    public MenuNet(Menu menu) {
        this.menu = menu;

        client = new Client();
        client.listenMessages(this);
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

    public void eventReact(NetworkEvent event) {
        System.out.println("Событие обработано: " + event.getData());
        Action action = CommandConverter.toMessage(event.getData());
        if(action.getCommand().getCode()==Commands.ENTER_IN_ROOM.getCode()){
            menu.enterInRoom((Integer) action.getValue().getFirst());
        } else if (action.getCommand().getCode()==Commands.SEND_ROOM_LIST.getCode()) {
            menu.createButtons(((List<String>) action.getValue())
                    .stream()
                    .map(Integer::parseInt)
                    .collect(Collectors.toList()));
        }
    }
}
