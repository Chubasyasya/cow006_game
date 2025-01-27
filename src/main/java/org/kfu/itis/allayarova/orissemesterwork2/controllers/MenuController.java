package org.kfu.itis.allayarova.orissemesterwork2.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kfu.itis.allayarova.orissemesterwork2.service.Game;
import org.kfu.itis.allayarova.orissemesterwork2.service.Menu;

import java.io.IOException;
import java.util.List;

public class MenuController extends BaseController{
    Menu menu;

    @FXML
    private VBox buttonContainer;

    @FXML
    public void initialize() {
        menu = new Menu(this);
    }

    public void createButtons(List<Integer> numbers) {
        buttonContainer.getChildren().clear();
        for (Integer number : numbers) {
            Button button = new Button("Room " + number);
            button.setOnAction(e -> {
                menu.enterInRoom(number);
            });
            buttonContainer.getChildren().add(button);
        }
    }

    public void loadRoomScene(int roomNumber, boolean beginGame) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/kfu/itis/allayarova/orissemesterwork2/roomScene.fxml"));
            Parent root = loader.load();

            RoomController roomController = loader.getController();

            roomController.setRoomNumber(roomNumber);

            if(beginGame){
                Game game = new Game(roomController, menu.getMenuNet());

                roomController.setGame(game);
                game.startGame();
//                roomController.startGame();
            }

            Stage stage = (Stage) buttonContainer.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
