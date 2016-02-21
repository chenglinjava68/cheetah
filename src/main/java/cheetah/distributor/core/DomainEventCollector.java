package cheetah.distributor.core;

import cheetah.distributor.event.AbstractCollector;
import cheetah.distributor.event.Event;

/**
 * Created by Max on 2016/2/17.
 */
public class DomainEventCollector extends AbstractCollector {


    public DomainEventCollector() {
    }

    public DomainEventCollector(DispatcherWorker dispatcher) {
        super(dispatcher);
    }

    @Override
    public void collect(Event event) {
    }

    @Override
    public void collect(Event event, int mode) {
    }

    @Override
    public void collect(Event event, int mode, boolean fisrtWin) {
    }


}
