package cheetah.machine.support;

import cheetah.event.*;
import cheetah.machine.AbstractMachine;
import cheetah.machine.EventHandlerException;
import cheetah.worker.Worker;

import java.util.EventListener;

/**
 * Created by Max on 2016/2/1.
 */
public class GenericEventMachine extends AbstractMachine {

    public GenericEventMachine(EventListener eventListener, Worker machinery) {
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
