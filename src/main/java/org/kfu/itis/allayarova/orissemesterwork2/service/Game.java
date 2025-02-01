package org.kfu.itis.allayarova.orissemesterwork2.service;

import org.kfu.itis.allayarova.orissemesterwork2.controllers.RoomController;
import org.kfu.itis.allayarova.orissemesterwork2.models.Action;
import org.kfu.itis.allayarova.orissemesterwork2.models.Card;
import org.kfu.itis.allayarova.orissemesterwork2.models.Deck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game{
    protected RoomController roomController;
    protected GameNet gameNet;

    public Game(RoomController controller, MenuNet menuNet) {
        gameNet = new GameNet(this, menuNet.getClient());

        this.roomController = controller;
    }


//    public void beginGame() {
//    }

    public void getCards(List<String> cardsId) {
        List<Card> cards = findCardsById(cardsId);

        roomController.getCards(cards);
    }

    public void fieldInit(List<String> cardsId) {
        List<Card> cards = findCardsById(cardsId);

        roomController.fieldInit(cards);
    }

    private List<Card> findCardsById(List<String> cardsId){
        List<Card> cards = new ArrayList<>();
        for(String cardId: cardsId){
            Card card = Deck.getCardById(Integer.parseInt(cardId));
            cards.add(card);
        }
        return cards;
    }

    public void sendCardToServer(Card card) {
        gameNet.send(new Action<Integer>(Commands.SELECT_CARD, Collections.singletonList(card.getNumber())));
    }

    public void selectRowToPick() {
        roomController.enableRowSelection();
    }

    public void sendRowToServer(int rowIndex) {
        gameNet.send(new Action<>(Commands.SELECT_ROW_TO_PICK, Collections.singletonList(rowIndex)));
    }

    public void updatePlayingField(List<String> field) {
        Card[][] playingField = new Card[4][6];
        for(int i = 0; i < field.size(); i++){
            int row = i/6;
            int col = i%6;
            int cardId = Integer.parseInt(field.get(i));
            Card card = cardId==-1? null:Deck.getCardById(cardId);
            playingField[row][col] = card;
        }
        roomController.updatePlayingField(playingField);
    }

    public void roundCompleted(int penaltyPoints) {
        roomController.roundCompleted(penaltyPoints);
    }

    public void gameResult(int result) {
        roomController.showResult(result);

    }
}
