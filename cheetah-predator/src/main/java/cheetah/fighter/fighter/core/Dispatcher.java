package cheetah.fighter.fighter.core;

import cheetah.fighter.fighter.protocol.Message;

/**
 * Created by Max on 2016/3/13.
 */
public interface Dispatcher {
    void dispatch(Message message);
}
