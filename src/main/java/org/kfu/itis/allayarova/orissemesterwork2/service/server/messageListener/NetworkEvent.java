package org.kfu.itis.allayarova.orissemesterwork2.service.server.messageListener;

public class NetworkEvent {
    private final String type;
    private final String data;

    public NetworkEvent(String type, String data) {
        this.type = type;
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public String getData() {
        return data;
    }
}

