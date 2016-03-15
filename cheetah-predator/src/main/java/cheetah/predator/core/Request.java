package cheetah.predator.core;

import cheetah.predator.protocol.Message;

/**
 * Created by Max on 2016/3/13.
 */
public interface Request {

    void setMessage(Message message);

    Message getMessage();

    Session getSession();
}
