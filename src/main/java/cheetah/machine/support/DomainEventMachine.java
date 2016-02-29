package cheetah.machine.support;

import cheetah.event.DomainEvent;
import cheetah.event.DomainEventListener;
import cheetah.event.Event;
import cheetah.machine.AbstractMachine;

import java.util.EventListener;

/**
 * Created by Max on 2016/2/1.
 */
public class DomainEventMachine extends AbstractMachine {

    public DomainEventMachine() {
    }

    public DomainEventMachine(EventListener eventListener) {
        super(eventListener);
    }

    protected void doExecute(Event event) {
        DomainEvent $event = (DomainEvent) event;
        ((DomainEventListener<DomainEvent>) getEventListener()).onDomainEvent($event);
    }

}
