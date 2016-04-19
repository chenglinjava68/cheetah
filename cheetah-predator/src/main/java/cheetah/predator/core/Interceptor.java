package cheetah.predator.core;

import cheetah.predator.protocol.MessageBuf;

/**
 * Created by Max on 2016/3/26.
 */
public interface Interceptor {
    boolean handle(MessageBuf.Message message, Session session) throws Exception;

    void afterCompletion(MessageBuf.Message message, Session session, Exception ex) throws Exception;

    boolean supportsType(int messageType);

}
