package org.cheetah.fighter.handler.support;

import org.cheetah.fighter.event.DomainEvent;
import org.cheetah.fighter.event.DomainEventListener;
import org.cheetah.fighter.event.Event;
import org.cheetah.fighter.handler.AbstractHandler;

import java.util.EventListener;

/**
 * Created by Max on 2016/2/1.
 */
public class DomainEventHandler extends AbstractHandler {

    public DomainEventHandler() {
    }

    public DomainEventHandler(EventListener eventListener) {
        super(eventListener);
    }

    protected void doExecute(Event event) {
        DomainEvent $event = (DomainEvent) event;
        DomainEventListener<DomainEvent> listener = (DomainEventListener<DomainEvent>) getEventListener();
        listener.onDomainEvent($event);
        listener.onFinish();
    }

}
