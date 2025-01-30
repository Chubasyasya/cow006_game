package org.kfu.itis.allayarova.orissemesterwork2.models;

import org.kfu.itis.allayarova.orissemesterwork2.ClientHandler;
import org.kfu.itis.allayarova.orissemesterwork2.service.Commands;

import java.util.*;

public class GameState {
    private Card[][] playingField;
    private Card[] lastInRows;
    private List<Card> deck;
    private List<Card> usedCards;
    
    private Map<Card, ClientHandler> clientAndSelectedCards;
    private List<Card> sortedChosenCards;

    public synchronized Card getCard() {
        Card card = deck.getFirst();
        clientAndSelectedCards = new HashMap<>();
        usedCards.add(card);
        deck.removeFirst();
        return card;
    }

    public GameState() {
        this.deck = Deck.getCards();
        Collections.shuffle(deck);

        this.usedCards = new ArrayList<>();
    }

    public void playingFieldInit(){
        playingField = new Card[4][6];
        lastInRows = new Card[4];
    }

    public void setCardOnPlayingField(int indexX, int indexY, Card card) {
        playingField[indexX][indexY] = card;
    }

//    public void addClientAndSelectedCards(ClientHandler clientHandler, int cardId) {
//        clientAndSelectedCards.put(getUsedCardById(cardId), clientHandler);
//    }

    private Card getUsedCardById(int id){
        for(Card card:usedCards){
            if(card.getNumber()==id){
                return card;
            }
        }
        return null;
    }

    public Map<Card, ClientHandler> getClientAndSelectedCards() {
        return clientAndSelectedCards;
    }

    public void setClientAndSelectedCards(Map<Card, ClientHandler> clientAndSelectedCards) {
        this.clientAndSelectedCards = clientAndSelectedCards;
    }

    public void rangingCards() {
        List<Card> sortedCards = new ArrayList<>(clientAndSelectedCards.keySet());
        Collections.sort(sortedCards);
        sortedChosenCards = sortedCards;
    }

    private void nullRow(int minDiffRow) {
        for(int i = 0; i < 6; i++){
            playingField[minDiffRow][i]=null;
        }
    }

    public void setLastInRows(int i, Card card) {
        lastInRows[i] = card;
    }

    public List<Card> getSortedChosenCards() {
        return sortedChosenCards;
    }

    public Commands putCardOnTable(int cardId, Player player) {
        Card card = getUsedCardById(cardId);
        int minDiff = 104;
        int minDiffRow = -1;
        for(int i = 0; i < 4; i++){
            Card lastInRowCard = lastInRows[i];
            int diff = cardId-lastInRowCard.getNumber();
            if(lastInRowCard.getNumber()<cardId && minDiff>diff && diff>0){
                minDiff = diff;
                minDiffRow = i;
            }
        }

        if(minDiffRow==-1){
             return Commands.SELECT_ROW_TO_PICK;
        } else {
            lastInRows[minDiffRow] = card;
            for (int i = 0; i < 6; i++) {
                if (playingField[minDiffRow][i] == null) {
                    playingField[minDiffRow][i] = card;
                    if (i == 5) {
                        nullRow(minDiffRow);
                        playingField[minDiffRow][0] = card;
                        player.addPenaltyPoints(card.getPenaltyPoints());
                        return Commands.NOTIFY_NEXT;
                    }
                    break;
                }
            }
            if(sortedChosenCards.getLast().equals(card)){
                clientAndSelectedCards.clear();
                return Commands.ROUND_COMPLETED;
            }
            return Commands.NOTIFY_NEXT;
        }
    }

    public ClientHandler getNextClientHandlerByCurrentCardId(int cardId) {
        for(int i = 0; i < sortedChosenCards.size(); i++){
            if(sortedChosenCards.get(i).getNumber() > cardId){
                return clientAndSelectedCards.get(sortedChosenCards.get(i));
            }
        }
        return null;
    }

    public int getNextCardId(int currentCardId){
        for(int i = 0; i < sortedChosenCards.size();i++){
            if(currentCardId < sortedChosenCards.get(i).getNumber()){
                return sortedChosenCards.get(i).getNumber();
            }
        }
        return -1;
    }

    public String getPlayingFieldString() {
        StringBuilder sb = new StringBuilder();

        for (Card[] row : playingField) {
            for (Card card : row) {
                sb.append(card != null ? card.getNumber() : -1).append(" ");
            }
        }

        return sb.toString();
    }

    public void pickRowCards(int rowNumber, ClientHandler clientHandler) {
        int penaltyPoints = 0;
        for(int i =0; i < 6; i++) {
            if(playingField[rowNumber][i]!=null) {
                penaltyPoints += playingField[rowNumber][i].getPenaltyPoints();
                playingField[rowNumber][i] = null;
            }else{
                break;
            }
        }
        Card card = getCardByClientHandler(clientHandler);
        clientHandler.getPlayer().setPenaltyPoints(penaltyPoints);
        playingField[rowNumber][0] = card;
        lastInRows[rowNumber] = card;
    }

    public Card getCardByClientHandler(ClientHandler handler) {
        for (Map.Entry<Card, ClientHandler> entry : clientAndSelectedCards.entrySet()) {
            if (entry.getValue().equals(handler)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public synchronized void addClientAndSelectedCards(ClientHandler clientHandler, int cardId, int size) {
        clientAndSelectedCards.put(getUsedCardById(cardId), clientHandler);

        if (allPlayersSelectedCards(size)) {
            synchronized (this) {
                notifyAll();
            }
        }
    }

    public boolean allPlayersSelectedCards(int size) {
        System.out.println("Client and cards" + clientAndSelectedCards.keySet());
        return clientAndSelectedCards.size() == size;
    }

}
