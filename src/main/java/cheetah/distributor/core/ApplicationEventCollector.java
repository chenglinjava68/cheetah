package cheetah.distributor.core;

import cheetah.distributor.event.AbstractCollector;
import cheetah.distributor.event.Event;

/**
 * Created by Max on 2016/2/3.
 */
public class ApplicationEventCollector extends AbstractCollector {
    public ApplicationEventCollector() {
    }

    protected ApplicationEventCollector(DispatcherWorker dispatcher) {
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
