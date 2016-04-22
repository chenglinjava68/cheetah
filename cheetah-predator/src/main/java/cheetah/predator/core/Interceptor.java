package cheetah.predator.core;

/**
 * Created by Max on 2016/3/26.
 */
public interface Interceptor {
    boolean handle(Message message, Session session) throws Exception;

    void afterCompletion(Message message, Session session, Exception ex) throws Exception;

    boolean supportsType(MessageType  type);

}
