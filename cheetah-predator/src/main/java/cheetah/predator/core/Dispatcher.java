package cheetah.predator.core;

import cheetah.predator.protocol.ProtocolConvertor;

/**
 * Created by Max on 2016/3/13.
 */
public interface Dispatcher {
    void dispatch(ProtocolConvertor.Message message);
}
