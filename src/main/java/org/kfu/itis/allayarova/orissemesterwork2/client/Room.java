package org.kfu.itis.allayarova.orissemesterwork2.client;

import org.kfu.itis.allayarova.orissemesterwork2.ClientHandler;

import java.util.List;

public class Room {
    int size;
    List<ClientHandler> clients;

    GameState state;
    private int id;

    public List<ClientHandler> getClients() {
        return clients;
    }

    public void setClientHandlers(List<ClientHandler> clients) {
        this.clients = clients;
    }
}
