package org.kfu.itis.allayarova.orissemesterwork2.server;

import org.kfu.itis.allayarova.orissemesterwork2.models.Card;
import org.kfu.itis.allayarova.orissemesterwork2.models.Deck;
import org.kfu.itis.allayarova.orissemesterwork2.models.Player;
import org.kfu.itis.allayarova.orissemesterwork2.service.Commands;

import java.util.*;
import java.util.stream.Collectors;

public class GameState {
    private Room room;
    private Card[][] playingField;
    private Card[] lastInRows;
    private List<Card> deck;
    private List<Card> usedCards;
    
    private Map<Card, ClientHandler> clientAndSelectedCards;
    private List<Card> sortedChosenCards;
    private List<Player> playersRangedByPoints;
    private int moveCounter;

    public GameState(Room room) {
        this.room = room;
        playersRangedByPoints = new ArrayList<>();

        this.deck = new ArrayList<>(Deck.getCards());
        Collections.shuffle(this.deck);
        moveCounter = 0;

        this.usedCards = new ArrayList<>();
    }

    public synchronized Card getCard() {
        Card card = deck.getFirst();
        clientAndSelectedCards = new HashMap<>();
        usedCards.add(card);
        deck.removeFirst();
        return card;
    }

    public int getMoveCounter() {
        return moveCounter;
    }

    private Card getUsedCardById(int id){
        for(Card card:usedCards){
            if(card.getNumber()==id){
                return card;
            }
        }
        return null;
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

    public void setClientAndSelectedCards(Map<Card, ClientHandler> clientAndSelectedCards) {
        this.clientAndSelectedCards = clientAndSelectedCards;
    }

    public void setMoveCounter(int moveCounter) {
        this.moveCounter = moveCounter;
    }

    public void setCardOnPlayingField(int indexX, int indexY, Card card) {
        playingField[indexX][indexY] = card;
    }
    public void setLastInRows(int i, Card card) {
        lastInRows[i] = card;
    }

    public void playingFieldInit(){
        playingField = new Card[4][6];
        lastInRows = new Card[4];
    }

    public void updateMoveCounter(){
        moveCounter++;
    }


    public void rangingCards() {
        List<Card> sortedCards = new ArrayList<>(clientAndSelectedCards.keySet());
        Collections.sort(sortedCards);
        sortedChosenCards = sortedCards;
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
            int points = 0;
            for (int i = 0; i < 6; i++) {
                if(playingField[minDiffRow][i] != null) {
                    points += playingField[minDiffRow][i].getPenaltyPoints();
                }else {
                    playingField[minDiffRow][i] = card;
                    if (i == 5) {
                        nullRow(minDiffRow);
                        playingField[minDiffRow][0] = card;
                        player.addPenaltyPoints(points+card.getPenaltyPoints());
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

    private void nullRow(int minDiffRow) {
        for(int i = 0; i < 6; i++){
            playingField[minDiffRow][i]=null;
        }
    }

    public void pickRowCards(int rowNumber, ClientHandler clientHandler) {
        Player player = clientHandler.getPlayer();
        int penaltyPoints = player.getPenaltyPoints();

        for(int i =0; i < 6; i++) {
            if(playingField[rowNumber][i]!=null) {
                penaltyPoints += playingField[rowNumber][i].getPenaltyPoints();
                playingField[rowNumber][i] = null;
            }else{
                break;
            }
        }

        Card card = getCardByClientHandler(clientHandler);
        player.setPenaltyPoints(penaltyPoints);

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
        System.out.println("Client and cards" + clientAndSelectedCards.keySet().stream().map(x -> x.getNumber()).collect(Collectors.toSet()));
        return clientAndSelectedCards.size() == size;
    }

    public void updateGameState() {
        deck = new ArrayList<>(Deck.getCards());
        Collections.shuffle(deck);

        usedCards.clear();
        clientAndSelectedCards.clear();
        sortedChosenCards.clear();
        moveCounter = 0;
    }

    public void sortPlayers(){
        Collections.sort(playersRangedByPoints);
    }
    public List<ClientHandler> determineLosers() {
        List<ClientHandler> result = new ArrayList<>();
        if (playersRangedByPoints.isEmpty()) return result;

        int maxValue = playersRangedByPoints.getLast().getPenaltyPoints();

        for (int i = playersRangedByPoints.size() - 1; i >= 0; i--) {
            if (playersRangedByPoints.get(i).getPenaltyPoints() == maxValue) {
                result.add(playersRangedByPoints.get(i).getClientHandler());
            } else {
                break;
            }
        }
        return result;
    }

    public List<ClientHandler> determineWinners() {
        List<ClientHandler> result = new ArrayList<>();
        if (playersRangedByPoints.isEmpty()) return null;

        int minValue = playersRangedByPoints.getFirst().getPenaltyPoints();

        for (int i = 0; i < playersRangedByPoints.size(); i++) {
            if (playersRangedByPoints.get(i).getPenaltyPoints() == minValue) {
                result.add(playersRangedByPoints.get(i).getClientHandler());
            } else {
                break;
            }
        }
        return result;
    }

    public List<ClientHandler> determineAvgPlayers() {
        List<ClientHandler> result = new ArrayList<>();
        if (playersRangedByPoints.isEmpty()) return null;

        int minValue = playersRangedByPoints.getFirst().getPenaltyPoints();
        int maxValue = playersRangedByPoints.getLast().getPenaltyPoints();

        for (int i = 0; i < playersRangedByPoints.size(); i++) {
            int penPoints = playersRangedByPoints.get(i).getPenaltyPoints();
            if (penPoints != minValue && penPoints!=maxValue) {
                result.add(playersRangedByPoints.get(i).getClientHandler());
            }
        }
        return result;
    }

    public List<Player> getPlayersRangedByPoints() {
        return playersRangedByPoints;
    }

    public void setPlayersRangedByPoints(Set<ClientHandler> clients) {
        for(ClientHandler clientHandler: clients){
            playersRangedByPoints.add(clientHandler.getPlayer());
        }
    }
}
