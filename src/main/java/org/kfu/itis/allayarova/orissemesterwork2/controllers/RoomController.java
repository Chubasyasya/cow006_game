package org.kfu.itis.allayarova.orissemesterwork2.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.kfu.itis.allayarova.orissemesterwork2.models.Card;
import org.kfu.itis.allayarova.orissemesterwork2.service.Game;

import java.util.List;

public class RoomController {
    private Game game;

    @FXML
    private VBox rightContainer;

    @FXML
    private ImageView deckImageView;
    private boolean isDeckEmpty = false;
    
    @FXML
    public void initialize(){
        Image deckImage = new Image(getClass().getResource("/org/kfu/itis/allayarova/orissemesterwork2/images/card_back2.png").toExternalForm());
        deckImageView.setImage(deckImage);

        checkDeckStatus();
    }

    public void setDeckEmpty(boolean isDeckEmpty) {
        this.isDeckEmpty = isDeckEmpty;
        checkDeckStatus();
    }

    private void checkDeckStatus() {
        if (isDeckEmpty) {
            deckImageView.setVisible(false);
        } else {
            deckImageView.setVisible(true);
        }
    }

    public void setRoomNumber(int roomNumber) {
    }

    public void startGame(){

    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void getCards(List<Card> cards) {
    }
}
