package org.kfu.itis.allayarova.orissemesterwork2.client.messageListener;

import org.kfu.itis.allayarova.orissemesterwork2.models.Message;

import java.util.ArrayList;
import java.util.List;

public class EventDispatcher {
    private final List<EventListener> listeners = new ArrayList<>();

    public void addListener(EventListener listener) {
        listeners.add(listener);
    }

    public void removeListener(EventListener listener) {
        listeners.remove(listener);
    }

    public void dispatch(Message message) {
        for (EventListener listener : listeners) {
            listener.onEvent(message);
        }
    }

    public List<EventListener> getListeners() {
        return listeners;
    }
}
