package org.kfu.itis.allayarova.orissemesterwork2.models;

import org.kfu.itis.allayarova.orissemesterwork2.server.ClientHandler;

import java.util.List;

public class Player implements Comparable<Player>{
    private ClientHandler clientHandler;
    private String name;
    private int penaltyPoints;
    private List<Card> cards;

    public Player(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPenaltyPoints() {
        return penaltyPoints;
    }

    public void setPenaltyPoints(int penaltyPoints) {
        this.penaltyPoints = penaltyPoints;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public void addPenaltyPoints(int penaltyPoints) {
        this.penaltyPoints+=penaltyPoints;
    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    @Override
    public int compareTo(Player o) {
        return Integer.compare(this.penaltyPoints, o.getPenaltyPoints());
    }

}
