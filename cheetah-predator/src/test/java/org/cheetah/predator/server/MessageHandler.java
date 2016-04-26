package org.cheetah.predator.server;

import org.cheetah.predator.core.Interceptor;
import org.cheetah.predator.core.Message;
import org.cheetah.predator.core.MessageType;
import org.cheetah.predator.core.Session;

/**
 * Created by Max on 2016/4/22.
 */
public class MessageHandler implements Interceptor {
    @Override
    public boolean handle(Message message, Session session) throws Exception {
        return false;
    }

    @Override
    public void afterCompletion(Message message, Session session, Exception ex) throws Exception {

    }

    @Override
    public boolean supportsType(MessageType type) {
        return true;
    }
}