package org.kfu.itis.allayarova.orissemesterwork2.models;

import java.util.*;

public class GameState {
    private List<Card> deck;
    private List<Card> usedCards;

    public synchronized Card getCard() {
        Card card = deck.getFirst();
        usedCards.add(card);
        deck.removeFirst();
        return card;
    }

    public GameState() {
        this.deck = Deck.getCards();
        Collections.shuffle(deck);

        this.usedCards = new ArrayList<>();
    }
}
