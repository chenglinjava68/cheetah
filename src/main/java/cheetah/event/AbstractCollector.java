package cheetah.event;


import cheetah.core.support.DispatcherMachine;

/**
 * 事件收集器的抽象类
 * Created by Max on 2016/2/3.
 */
public abstract class AbstractCollector implements Collector {
    private DispatcherMachine dispatcher;

    public AbstractCollector() {
    }

    public AbstractCollector(DispatcherMachine dispatcher) {
        this.dispatcher = dispatcher;
    }

    public DispatcherMachine getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(DispatcherMachine dispatcher) {
        this.dispatcher = dispatcher;
    }
}
