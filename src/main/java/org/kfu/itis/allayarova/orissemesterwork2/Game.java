package org.kfu.itis.allayarova.orissemesterwork2;

import javafx.application.Platform;
import org.kfu.itis.allayarova.orissemesterwork2.client.Client;
import org.kfu.itis.allayarova.orissemesterwork2.controllers.BaseController;
import org.kfu.itis.allayarova.orissemesterwork2.controllers.MenuController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game {
    private MenuController controller;
    private Client client;

    public Game(BaseController controller) {
        client = new Client();
        client.listenMessages(this);

        this.controller = (MenuController) controller;
    }

    public void handleMessage(String message) {
        //короче мне тут надо сделать универсальную обработку а не только для кнопок например мне тут отправляют 01 это 0-код вохода в комнтатц 1-удачно воешл или нет
        List<Integer> numbers = parseNumbers(message);

        Platform.runLater(() -> {
            if (controller != null) {
                controller.createButtons(numbers);
            }
        });
    }

    private List<Integer> parseNumbers(String line) {
        List<Integer> numbers = new ArrayList<>();
        String[] parts = line.split(" ");

        Arrays.stream(parts).forEach(part -> {
            try {
                numbers.add(Integer.parseInt(part.trim()));
            } catch (NumberFormatException e) {
                System.out.println("Некорректное число: " + part);
            }
        });
        return  numbers;
    }

    public void enterInRoom(Integer roomId) {
        client.sendMessage(""+0+roomId);
    }
}
