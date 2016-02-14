package cheetah.distributor.handler;

import java.util.EventListener;
import java.util.concurrent.ExecutorService;

/**
 * Created by Max on 2016/2/1.
 */
public abstract class AbstractHandler implements Handler {
    private final EventListener eventListener;
    private final ExecutorService executorService;

    public AbstractHandler(EventListener eventListener, ExecutorService executorService) {
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
