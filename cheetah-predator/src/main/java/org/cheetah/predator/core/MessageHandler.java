package org.cheetah.predator.core;

/**
 * Created by Max on 2016/4/25.
 */
public interface MessageHandler {
    void handle(Message message);

    boolean supportsType(MessageType type);
}
