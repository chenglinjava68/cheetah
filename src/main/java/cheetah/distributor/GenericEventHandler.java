package cheetah.distributor;

import cheetah.event.*;
import cheetah.exceptions.EventHandlerException;

import java.util.EventListener;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

/**
 * Created by Max on 2016/2/1.
 */
public class GenericEventHandler extends AbstractHandler {
    public GenericEventHandler(EventListener eventListener, ExecutorService executorService) {
        super(eventListener, executorService);
    }

    @Override
    public Handler handle(Event event) {
        CompletableFuture future = CompletableFuture.supplyAsync(() -> {
            if (this.getEventListener().getClass().isAssignableFrom(ApplicationListener.class)) {
                ApplicationListener applicationListener = (ApplicationListener) this.getEventListener();
                ApplicationEvent $event = (ApplicationEvent) event;
                applicationListener.onApplicationEvent($event);
                return new EventResult<>(event.getSource());
            } else if (this.getEventListener().getClass().isAssignableFrom(DomainEventListener.class)) {
                DomainEventListener domainEventListener = (DomainEventListener) this.getEventListener();
                DomainEvent domainEvent = (DomainEvent) event;
                domainEventListener.onDomainEvent(domainEvent);
                return new EventResult<>(domainEvent.getSource());
            } else
                throw new EventHandlerException("[cheetah-distributor] : Generic event handler handle type error");
        }, getExecutorService());
        this.setFuture(future);
        return this;
    }

}
