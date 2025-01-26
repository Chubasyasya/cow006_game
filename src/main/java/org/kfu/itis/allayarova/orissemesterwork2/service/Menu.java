package org.kfu.itis.allayarova.orissemesterwork2.service;

import javafx.application.Platform;
import org.kfu.itis.allayarova.orissemesterwork2.controllers.BaseController;
import org.kfu.itis.allayarova.orissemesterwork2.controllers.MenuController;
import org.kfu.itis.allayarova.orissemesterwork2.models.Action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Menu {
    private MenuController controller;
    private MenuNet menuNet;

    public Menu(BaseController controller) {
        menuNet = new MenuNet(this);

        this.controller = (MenuController) controller;
    }

    public void createButtons(List<Integer> buttonsId) {

        Platform.runLater(() -> {
            if (controller != null) {
                controller.createButtons(buttonsId);
            }
        });
    }

    public void enterInRoom(Integer roomId) {
        menuNet.send(new Action(Commands.ENTER_IN_ROOM, new ArrayList<Integer>(roomId)));
    }


}
