package cheetah.distributor;

import cheetah.domain.AbstractEntity;
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
    public Handler handle(Event event) {
        CompletableFuture<EventResult> future = CompletableFuture.supplyAsync(() -> {
            DomainEventListener listener = (DomainEventListener) this.getEventListener();
            DomainEvent<AbstractEntity> domainEvent = (DomainEvent<AbstractEntity>) event;
            listener.onDomainEvent(domainEvent);
            return new EventResult<>(domainEvent.getSource());
        }, getExecutorService());
        this.setFuture(future);
        return this;
    }
}
