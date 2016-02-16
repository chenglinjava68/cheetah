package cheetah.distributor.handler;

import cheetah.event.DomainEvent;
import cheetah.event.DomainEventListener;
import cheetah.event.Event;

import java.util.EventListener;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

/**
 * Created by Max on 2016/2/1.
 */
public class DomainEventHandler extends AbstractHandler {

    public DomainEventHandler(EventListener eventListener, ExecutorService executorService) {
        super(eventListener, executorService);
    }

    @Override
    protected CompletableFuture<Boolean> statelessHandle(Event event) {
        return null;
    }

    @Override
    protected CompletableFuture<Boolean> statefulHandle(Event event) {
        return CompletableFuture.supplyAsync(() -> {
            DomainEventListener<DomainEvent> listener = (DomainEventListener<DomainEvent>) this.getEventListener();
            DomainEvent domainEvent = (DomainEvent) event;
            listener.onDomainEvent(domainEvent);
            return Boolean.TRUE;
        }, getExecutorService());
    }

}
