package org.kfu.itis.allayarova.orissemesterwork2.models;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Deck {
    private static List<Card> cards;
    private static Map<Integer, Card> cardsMap;

    static {
        cards = new ArrayList<>();
        cardsMap = new HashMap<>();
        loadCardsFromResources("src/main/resources/org/kfu/itis/allayarova/orissemesterwork2/images/cards");
    }

    private static void loadCardsFromResources(String folderPath) {
        String imagePath =  folderPath.substring(18);
        File folder = new File(folderPath);
        if (!folder.exists() || !folder.isDirectory()) {
            throw new IllegalArgumentException("Папка с картами не найдена: " + folderPath);
        }

        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".png")) {
                    String fileName = file.getName();
                    int number = Integer.parseInt(fileName.replace(".png", ""));
                    int penaltyPoints = 1;

                    if(number%5==0){
                       penaltyPoints = 2;
                       if(number%10==0){
                           penaltyPoints++;
                       }
                    }else if(number%11==0){
                        penaltyPoints=5;
                        if(number==55){
                            penaltyPoints+=2;
                        }
                    }

                    String fullImagePath = imagePath + "/" + fileName;
                    Card card = new Card(number, penaltyPoints, fullImagePath);
                    cards.add(card);
                    cardsMap.put(number, card);
                }
            }
        }
    }

    public static List<Card> getCards() {
        return cards;
    }

    public static Card getCardById(Integer cardId) {
        return cardsMap.get(cardId);
    }
}

