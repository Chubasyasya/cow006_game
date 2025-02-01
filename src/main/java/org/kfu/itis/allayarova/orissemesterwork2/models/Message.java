package org.kfu.itis.allayarova.orissemesterwork2.models;

import org.kfu.itis.allayarova.orissemesterwork2.service.Commands;

import java.time.Instant;

public class Message {
    private final int type;
    private final String data;
    private Instant created;
    private byte priority;


    public Message(int type, String data) {
        this.type = type;
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public String getData() {
        return data;
    }

    public Instant getCreated() {
        return created;
    }

    public byte getPriority() {
        return priority;
    }
}
