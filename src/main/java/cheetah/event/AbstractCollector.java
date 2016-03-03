package cheetah.event;


import cheetah.handler.support.DispatcherHandler;

/**
 * 事件收集器的抽象类
 * Created by Max on 2016/2/3.
 */
public abstract class AbstractCollector implements Collector {
    private DispatcherHandler dispatcher;

    public AbstractCollector() {
    }

    public AbstractCollector(DispatcherHandler dispatcher) {
        this.dispatcher = dispatcher;
    }

    public DispatcherHandler getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(DispatcherHandler dispatcher) {
        this.dispatcher = dispatcher;
    }
}
