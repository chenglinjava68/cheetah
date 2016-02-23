package cheetah.distributor.machine.support;

import cheetah.distributor.event.Event;
import cheetah.distributor.machine.AbstractMachine;
import cheetah.distributor.machine.Feedback;
import cheetah.logger.Debug;

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


    @Override
    public void execute(Event event) {
        Debug.log(this.getClass(), "DomainEventWorker...");

    }

    @Override
    public Feedback completeWork(Event event) {
        return null;
    }
}
