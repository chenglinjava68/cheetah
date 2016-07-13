package org.cheetah.fighter.handler;

import org.cheetah.commons.logger.Info;
import org.cheetah.fighter.core.event.DomainEvent;
import org.cheetah.fighter.core.event.DomainEventListener;
import org.cheetah.fighter.core.event.Event;
import org.cheetah.fighter.core.handler.AbstractHandler;

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
        Info.log(this.getClass(), "DomainEventHandler do Execute event {}", event);
        DomainEvent $event = (DomainEvent) event;
        DomainEventListener<DomainEvent> listener = (DomainEventListener<DomainEvent>) getEventListener();
        listener.onDomainEvent($event);
        listener.onFinish();
    }

}
