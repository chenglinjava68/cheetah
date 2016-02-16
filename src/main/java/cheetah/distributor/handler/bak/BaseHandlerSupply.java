package cheetah.distributor.handler.bak;

import cheetah.util.Assert;

import java.util.EventListener;
import java.util.concurrent.ExecutorService;

/**
 * Created by Max on 2016/2/1.
 */
public abstract class BaseHandlerSupply {
    private final EventListener eventListener;
    private final ExecutorService executorService;

    public BaseHandlerSupply(EventListener eventListener, ExecutorService executorService) {
        Assert.notNull(eventListener, "eventListener must not be null");
        Assert.notNull(executorService, "executorService must not be null");
        this.eventListener = eventListener;
        this.executorService = executorService;
    }

    public final EventListener getEventListener() {
        return eventListener;
    }

    public final ExecutorService getExecutorService() {
        return executorService;
    }

}
