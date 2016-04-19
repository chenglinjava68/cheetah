package cheetah.predator.core.support;

import cheetah.predator.core.Interceptor;
import cheetah.predator.core.Session;
import cheetah.predator.protocol.MessageBuf;

/**
 * Created by Max on 2016/3/26.
 */
public class AbstractMessageHandler implements Interceptor {
    @Override
    public boolean handle(MessageBuf.Message message, Session session) throws Exception {
        return false;
    }

    @Override
    public void afterCompletion(MessageBuf.Message message, Session session, Exception ex) throws Exception {

    }

    @Override
    public boolean supportsType(int messageType) {
        return false;
    }
}
