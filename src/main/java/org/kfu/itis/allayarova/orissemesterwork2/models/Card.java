package org.kfu.itis.allayarova.orissemesterwork2.models;

public class Card {
    private int number;
    private int penaltyPoints;
    private String imagePath;

    public Card(int number, int penaltyPoints, String imagePath) {
        this.number = number;
        this.penaltyPoints = penaltyPoints;
        this.imagePath = imagePath;
    }

    public int getNumber() {
        return number;
    }

    public int getPenaltyPoints() {
        return penaltyPoints;
    }

    public String getImagePath() {
        return imagePath;
    }
}
