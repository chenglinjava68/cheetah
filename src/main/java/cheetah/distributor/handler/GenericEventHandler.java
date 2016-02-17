package cheetah.distributor.handler;

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
    public CompletableFuture<Boolean> statefulHandle(Event event) {
        return CompletableFuture.supplyAsync(() -> {
            if (this.getEventListener().getClass().isAssignableFrom(ApplicationListener.class)) {
                ApplicationListener applicationListener = (ApplicationListener) this.getEventListener();
                ApplicationEvent $event = (ApplicationEvent) event;
                applicationListener.onApplicationEvent($event);
                return Boolean.TRUE;
            } else if (this.getEventListener().getClass().isAssignableFrom(DomainEventListener.class)) {
                DomainEventListener domainEventListener = (DomainEventListener) this.getEventListener();
                DomainEvent $event = (DomainEvent) event;
                domainEventListener.onDomainEvent($event);
                return Boolean.TRUE;
            } else
                throw new EventHandlerException("[cheetah-distributor] : Generic event handler handle type error");
        }, getExecutorService());
    }

    @Override
    public void statelessNativeAsyncHandle(Event event) {
        if (this.getEventListener().getClass().isAssignableFrom(ApplicationListener.class))
            getExecutorService().execute(() ->
                    ((ApplicationListener<ApplicationEvent>) this.getEventListener())
                            .onApplicationEvent((ApplicationEvent) event));
        else if (this.getEventListener().getClass().isAssignableFrom(DomainEventListener.class)) {
            getExecutorService().execute(() ->
                    ((DomainEventListener<DomainEvent>) this.getEventListener())
                            .onDomainEvent((DomainEvent) event));
        } else
            throw new EventHandlerException("[cheetah-distributor] : Generic event handler handle type error");

    }

    @Override
    public void statelessHandle(Event event) {

    }
}
