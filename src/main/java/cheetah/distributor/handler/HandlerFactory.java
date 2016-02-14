package cheetah.distributor.handler;

import java.util.EventListener;
import java.util.concurrent.ExecutorService;

/**
 * Created by Max on 2016/2/2.
 */
public abstract class HandlerFactory {
    private HandlerFactory() {
    }

    public static Handler createDomainEventHandler(EventListener eventListener, ExecutorService executorService, boolean needResult) {
        return new DomainEventHandler(eventListener, executorService,needResult);
    }

    public static Handler createApplicationEventHandler(EventListener eventListener, ExecutorService executorService, boolean needResult) {
        return new ApplicationEventHandler(eventListener, executorService,needResult);
    }

    public static Handler createGenericEventHandler(EventListener eventListener, ExecutorService executorService, boolean needResult) {
        return new GenericEventHandler(eventListener, executorService,needResult);
    }
}
