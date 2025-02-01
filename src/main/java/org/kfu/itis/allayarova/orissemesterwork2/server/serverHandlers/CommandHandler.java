package org.kfu.itis.allayarova.orissemesterwork2.server.serverHandlers;

import org.kfu.itis.allayarova.orissemesterwork2.server.ClientHandler;

import java.util.List;

public interface CommandHandler<E>{
    String handle(ClientHandler clientHandler, List<E> value);
}
