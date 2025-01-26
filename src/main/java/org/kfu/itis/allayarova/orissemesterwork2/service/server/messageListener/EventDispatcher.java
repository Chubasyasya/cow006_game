package org.kfu.itis.allayarova.orissemesterwork2.service.server.messageListener;

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

    public void dispatch(NetworkEvent event) {
        for (EventListener listener : listeners) {
            listener.onEvent(event);
        }
    }
}
