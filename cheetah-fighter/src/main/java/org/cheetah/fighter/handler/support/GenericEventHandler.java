package org.cheetah.fighter.handler.support;

import org.cheetah.fighter.handler.AbstractHandler;
import org.cheetah.fighter.handler.EventHandlerException;
import org.cheetah.fighter.worker.Worker;
import org.cheetah.fighter.event.*;

import java.util.EventListener;

/**
 * Created by Max on 2016/2/1.
 */
public class GenericEventHandler extends AbstractHandler {

    public GenericEventHandler(EventListener eventListener, Worker machinery) {
        super(eventListener);
    }

    protected void doExecute(Event event) {
        if (this.getEventListener().getClass().isAssignableFrom(ApplicationListener.class)) {
            ApplicationListener applicationListener = (ApplicationListener) this.getEventListener();
            ApplicationEvent $event = (ApplicationEvent) event;
            applicationListener.onApplicationEvent($event);

        } else if (this.getEventListener().getClass().isAssignableFrom(DomainEventListener.class)) {
            DomainEventListener domainEventListener = (DomainEventListener) this.getEventListener();
            DomainEvent $event = (DomainEvent) event;
            domainEventListener.onDomainEvent($event);
        } else
            throw new EventHandlerException("[cheetah-distributor] : Generic event handler handle type error");
    }

}
