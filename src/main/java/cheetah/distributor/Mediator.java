package cheetah.distributor;

import cheetah.distributor.handler.Handler;

/**
 * Created by Max on 2016/2/1.
 */
public interface Mediator {
    void register(String name, Class<? extends Handler> handler);
}
