package cheetah.handler.support;

import cheetah.core.event.DomainEvent;
import cheetah.core.event.DomainEventListener;
import cheetah.core.event.Event;
import cheetah.handler.AbstractHandler;

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
        ((DomainEventListener<DomainEvent>) getEventListener()).onDomainEvent($event);
    }

}
