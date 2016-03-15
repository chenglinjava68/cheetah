package cheetah.fighter.handler.support;

import cheetah.fighter.event.DomainEvent;
import cheetah.fighter.event.DomainEventListener;
import cheetah.fighter.event.Event;
import cheetah.fighter.handler.AbstractHandler;

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
