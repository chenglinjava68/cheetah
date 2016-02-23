package cheetah.distributor.machine.support;

import cheetah.distributor.event.*;
import cheetah.distributor.worker.Worker;
import cheetah.distributor.machine.AbstractMachine;
import cheetah.distributor.machine.EventHandlerException;
import cheetah.distributor.machine.Feedback;
import cheetah.logger.Debug;

import java.util.EventListener;

/**
 * Created by Max on 2016/2/1.
 */
public class GenericEventMachine extends AbstractMachine {

    public GenericEventMachine(EventListener eventListener, Worker machinery) {
        super(eventListener);
    }

    @Override
    public void execute(Event event) {
        Debug.log(this.getClass(), "GenericEventWorker ...");
        try {
            doWork(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doWork(Event event) {
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

    @Override
    public Feedback completeWork(Event event) {
        try {
            doWork(event);
        } catch (Exception e) {
            e.printStackTrace();
            return Feedback.FAILURE;
        }
        return Feedback.SUCCESS;
    }
}
