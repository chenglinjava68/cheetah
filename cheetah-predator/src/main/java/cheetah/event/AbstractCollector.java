package cheetah.event;


import cheetah.core.support.DispatcherEvent;

/**
 * 事件收集器的抽象类
 * Created by Max on 2016/2/3.
 */
public abstract class AbstractCollector implements EventCollector {
    private DispatcherEvent dispatcher;

    public AbstractCollector() {
    }

    public AbstractCollector(DispatcherEvent dispatcher) {
        this.dispatcher = dispatcher;
    }

    public DispatcherEvent getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(DispatcherEvent dispatcher) {
        this.dispatcher = dispatcher;
    }
}
