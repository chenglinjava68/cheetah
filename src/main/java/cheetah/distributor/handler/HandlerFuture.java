package cheetah.distributor.handler;

import cheetah.distributor.EventMessage;

/**
 * Created by Max on 2016/2/16.
 */
public interface HandlerFuture {
    void call(EventMessage eventMessage);

    void setTimeout(int timeout);

    Object get();
}
