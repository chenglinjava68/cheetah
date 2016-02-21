package cheetah.distributor.event;


import cheetah.distributor.core.DispatcherWorker;

/**
 * Created by Max on 2016/2/3.
 */
public abstract class AbstractCollector implements Collector {
    private DispatcherWorker dispatcher;

    public AbstractCollector() {
    }

    protected AbstractCollector(DispatcherWorker dispatcher) {
        this.dispatcher = dispatcher;
    }

    public DispatcherWorker getDispatcher() {
        return dispatcher;
    }
}
