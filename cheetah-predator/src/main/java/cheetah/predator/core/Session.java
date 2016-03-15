package cheetah.predator.core;

import cheetah.predator.protocol.Message;

/**
 * Created by Max on 2016/3/13.
 */
public interface Session {

    void respond(Message message);

    void close(Message message) throws Exception;

}
