package org.kfu.itis.allayarova.orissemesterwork2.models;

import org.kfu.itis.allayarova.orissemesterwork2.ClientHandler;

import java.io.IOException;
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
        gameState = new GameState(this);
    }

    public Room() {
        this.clients = new HashSet<>();
    }

    public Set<ClientHandler> getClients() {
        return clients;
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public GameState getGameState() {
        return gameState;
    }
}
