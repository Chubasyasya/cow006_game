package org.kfu.itis.allayarova.orissemesterwork2.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.kfu.itis.allayarova.orissemesterwork2.models.Card;
import org.kfu.itis.allayarova.orissemesterwork2.service.Game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RoomController {
    private Game game;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button sendButton;

    @FXML
    private VBox rightContainer;

    @FXML
    private GridPane gridPane;

    @FXML
    private HBox cardsContainer;

    @FXML
    private Text points = new Text("0");
    private Card selectedCard = null;
    private Button selectedCardButton = null;
    private boolean isCardSent = false;
    private MenuController menuController;

    @FXML
    public void initialize(){
        Image deckImage = new Image(getClass().getResource("/org/kfu/itis/allayarova/orissemesterwork2/images/card_back2.png").toExternalForm());
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void getCards(List<Card> cards) {
        Platform.runLater(() -> {
            cardsContainer.getChildren().clear();

            for (Card card : cards) {
                String imagePath = getClass().getResource(card.getImagePath()).toExternalForm();
                Image cardImage = new Image(imagePath);
                if (cardImage.isError()) {
                    System.out.println("Ошибка загрузки изображения: " + imagePath);
                    cardImage.getException().printStackTrace();
                }

                ImageView cardImageView = new ImageView(cardImage);

                cardImageView.setFitHeight(150);
                cardImageView.setFitWidth(100);

                Button cardButton = new Button();
                cardButton.setGraphic(cardImageView);
                cardButton.setStyle("-fx-background-color: transparent;");
                cardButton.setPadding(Insets.EMPTY);
                cardButton.setMinSize(100, 150);
                cardButton.setMaxSize(100, 150);

                cardButton.setOnAction(event -> {
                    selectedCard = card;
                    selectedCardButton = cardButton;

                    if(!isCardSent) sendButton.setVisible(true);
                    if(!isCardSent) sendButton.setDisable(false);
                });

                cardsContainer.getChildren().add(cardButton);
            }
        });
    }

    @FXML
    private void sendSelectedCard() {
        if (selectedCard != null && !isCardSent) {
            isCardSent = true;
            sendButton.setDisable(true);
            selectedCardButton.setDisable(true);
            sendButton.setVisible(false);

            game.sendCardToServer(selectedCard);
            selectedCard = null;
        }
    }

    public void roundCompleted(int penaltyPoints) {
        Platform.runLater(() -> {
            isCardSent = false;
            sendButton.setDisable(false);
            points.setText(String.valueOf(penaltyPoints));
        });
    }

    public void fieldInit(List<Card> cards) {
        Platform.runLater(() -> {
            gridPane.getChildren().clear();

            int rowCount = gridPane.getRowConstraints().size();

            for (int i = 0; i < Math.min(cards.size(), rowCount); i++) {
                Card card = cards.get(i);

                gridPane.add(getCardImageView(card), 0, i);
            }
        });
    }

    public void updatePlayingField(Card[][] playingField) {
        Platform.runLater(() -> {
            gridPane.getChildren().clear();

            int rows = playingField.length;
            int cols = playingField[0].length;

            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    Card card = playingField[row][col];

                    if (card != null) {
                        gridPane.add(getCardImageView(card), col, row);
                    } else {
                        ImageView emptySlot = new ImageView();
                        emptySlot.setFitHeight(150);
                        emptySlot.setFitWidth(100);
                        emptySlot.setStyle("-fx-border-color: black; -fx-border-width: 6px;");

                        gridPane.add(emptySlot, col, row);
                    }
                }
            }
        });
    }

    public ImageView getCardImageView(Card card){
        String imagePath = getClass().getResource(card.getImagePath()).toExternalForm();
        Image cardImage = new Image(imagePath);
        ImageView cardImageView = new ImageView(cardImage);

        cardImageView.setFitHeight(150);
        cardImageView.setFitWidth(100);
        return cardImageView;
    }

    public void enableRowSelection() {
        Platform.runLater(() -> {
            System.out.println("Выбор ряда разрешен!");

            int rowCount = gridPane.getRowConstraints().size();
            for (int i = 0; i < rowCount; i++) {
                int rowIndex = i;
                Button selectRowButton = new Button("Выбрать ряд " + (rowIndex + 1));
                selectRowButton.setOnAction(event -> selectRowToPick(rowIndex));

                selectRowButton.setUserData(rowIndex);
                gridPane.add(selectRowButton, 1, rowIndex);
            }
        });
    }

    public void selectRowToPick(int rowIndex) {
        Platform.runLater(() -> {
            System.out.println("Игрок выбрал ряд: " + rowIndex);
            game.sendRowToServer(rowIndex);

            disableRowSelection();
        });
    }

    private void disableRowSelection() {
        Platform.runLater(() -> {
            System.out.println("Выбор ряда заблокирован!");

            List<Node> toRemove = new ArrayList<>();
            for (Node node : gridPane.getChildren()) {
                if (node instanceof Button && ((Button) node).getText().startsWith("Выбрать ряд")) {
                    toRemove.add(node);
                }
            }
            gridPane.getChildren().removeAll(toRemove);
        });
    }

    public void showResult(int result) {
        Platform.runLater(() -> {
            String message;
            if (result == 1) {
                message = "Ура! Ты выиграл!";
            } else if (result == 0) {
                message = "Ты проиграл! Попробуй снова!";
            } else {
                message = "Ты не проиграл и не выиграл!";
            }

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/kfu/itis/allayarova/orissemesterwork2/resultScene.fxml"));
                Parent root = loader.load();

                ResultController resultController = loader.getController();
                resultController.setResultMessage(message);

                Stage stage = (Stage) gridPane.getScene().getWindow();
                stage.setScene(new Scene(root));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
