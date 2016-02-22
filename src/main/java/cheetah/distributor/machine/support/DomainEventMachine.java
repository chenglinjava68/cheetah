package cheetah.distributor.machine.support;

import cheetah.distributor.event.Event;
import cheetah.distributor.worker.Worker;
import cheetah.distributor.machine.AbstractMachine;
import cheetah.distributor.machine.Report;
import cheetah.logger.Debug;

import java.util.EventListener;

/**
 * Created by Max on 2016/2/1.
 */
public class DomainEventMachine extends AbstractMachine {

    public DomainEventMachine() {
    }

    public DomainEventMachine(EventListener eventListener, Worker machinery) {
        super(eventListener, machinery);
    }


    @Override
    public void work(Event event) {
        Debug.log(this.getClass(), "DomainEventWorker...");

    }

    @Override
    public Report completeWork(Event event) {
        return null;
    }
}
