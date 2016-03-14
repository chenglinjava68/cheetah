package cheetah.fighter.core;

import cheetah.fighter.protocol.Message;

/**
 * Created by Max on 2016/3/13.
 */
public interface Request {

    void setMessage(Message message);

    Message getMessage();

    <T> Session<T> getSession();
}
