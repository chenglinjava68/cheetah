package cheetah.distributor.handler;

import java.util.EventListener;
import java.util.concurrent.ExecutorService;

/**
 * Created by Max on 2016/2/2.
 */
public class HandlerFactory {
    private ExecutorService executor;

    public HandlerFactory(ExecutorService executor) {
        this.executor = executor;
    }

    public Handler createDomainEventHandler(EventListener eventListener) {
        return new DomainEventHandler(eventListener, executor);
    }

    public Handler createApplicationEventHandler(EventListener eventListener) {
        return new ApplicationEventHandler(eventListener, executor);
    }

    public Handler createGenericEventHandler(EventListener eventListener) {
        return new GenericEventHandler(eventListener, executor);
    }
}
