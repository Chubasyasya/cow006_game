package org.kfu.itis.allayarova.orissemesterwork2.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kfu.itis.allayarova.orissemesterwork2.Game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuController extends BaseController{
    Game game;

    @FXML
    private VBox buttonContainer;

    @FXML
    public void initialize() {
        game = new Game(this);
    }

    public void createButtons(List<Integer> numbers) {
        buttonContainer.getChildren().clear();
        for (Integer number : numbers) {
            Button button = new Button("Room " + number);
            button.setOnAction(e -> {
                game.enterInRoom(number);
                loadRoomScene(number);
            });
            buttonContainer.getChildren().add(button);
        }
    }

    private void loadRoomScene(int roomNumber) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/kfu/itis/allayarova/orissemesterwork2/roomScene.fxml"));
            Parent root = loader.load();

            RoomController roomController = loader.getController();

            roomController.setRoomNumber(roomNumber);

            Stage stage = (Stage) buttonContainer.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
