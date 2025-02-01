package org.kfu.itis.allayarova.orissemesterwork2.client.messageListener;

import org.kfu.itis.allayarova.orissemesterwork2.models.Message;

public interface EventListener {
    void onEvent(Message message);
}
