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

    public void startGame() {
        gameNet.startGame(new Action<Integer>(Commands.START_GAME, Collections.singletonList(1)));
    }

    public void beginGame() {
        gameNet.send(new Action<>(Commands.GET_CARDS, Collections.singletonList(10)));
        roomController.startGame();
    }

    public void getCards(List<String> cardsId) {
        List<Card> cards = new ArrayList<>();
        for(String cardId: cardsId){
            Card card = Deck.getCardById(Integer.parseInt(cardId));
            cards.add(card);
        }

        roomController.getCards(cards);
    }
}
