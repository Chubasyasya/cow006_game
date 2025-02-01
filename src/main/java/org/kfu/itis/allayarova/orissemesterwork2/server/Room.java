package org.kfu.itis.allayarova.orissemesterwork2.server;

import org.kfu.itis.allayarova.orissemesterwork2.models.Player;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Room{
    int size;
    Set<ClientHandler> clients;
    GameState gameState;

    public Room(int size, Set<ClientHandler> clients) {
        this.size = size;
        this.clients = clients;
    }

    public Room() {
        this.clients = new HashSet<>();
    }

    public Set<ClientHandler> getClients() {
        return clients;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void sendMessage(List<ClientHandler> clientHandlers, String message) {
        for (ClientHandler clientHandler: clientHandlers){
            clientHandler.sendMessage(message);
        }
    }

    public boolean addClient(ClientHandler clientHandler) {
        if(size-clients.size()>0){
            clients.add(clientHandler);
            return true;
        }
        return false;
    }

    public synchronized void broadcastMessage(String message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }

    public  void sendPenaltyPoints(String message){
        for (ClientHandler client : clients) {
            Player player = client.getPlayer();
            client.sendMessage(message+player.getPenaltyPoints());
        }
    }

}
