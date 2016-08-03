package org.cheetah.fighter.engine;

import org.cheetah.common.logger.Info;
import org.cheetah.fighter.DomainEvent;
import org.cheetah.fighter.DomainEventListener;
import org.cheetah.fighter.EventContext;
import org.cheetah.fighter.HandlerMapping;
import org.cheetah.fighter.async.AsynchronousPoolFactory;
import org.cheetah.fighter.handler.Handler;
import org.cheetah.fighter.handler.HandlerFactory;
import org.cheetah.fighter.plugin.PluginChain;
import org.cheetah.fighter.worker.WorkerAdapterFactory;

/**
 * Created by Max on 2016/3/2.
 */
public abstract class AbstractEngine<T> implements Engine<T> {
    private WorkerAdapterFactory workerAdapterFactory;
    private HandlerFactory handlerFactory;
    private PluginChain pluginChain = new PluginChain();
    private AsynchronousPoolFactory asynchronousPoolFactory;
    @Deprecated
    private volatile HandlerMapping mapping;
    private EventContext context;
    protected State state;

    @Override
    public void start() {
        Info.log(this.getClass(), " Engine start ...");
        initialize();
        this.state = State.RUNNING;
    }

    public void initialize() {
        this.asynchronousPoolFactory.setEventContext(this.context);
        getAsynchronousPoolFactory().start();
    }

    @Override
    public void stop() {
        handlerFactory = null;
        pluginChain = null;
        asynchronousPoolFactory.stop();
        asynchronousPoolFactory = null;
        Info.log(this.getClass(), "Engine has been shutdown.");
        this.state = State.STOP;
    }

    @Override
    public boolean isRunning() {
        return state().equals(State.RUNNING);
    }

    @Override
    public Handler assignDomainEventHandler(DomainEventListener<DomainEvent> eventListener) {
        return handlerFactory.createDomainEventHandler(eventListener);
    }

    @Override
    public void setWorkerAdapterFactory(WorkerAdapterFactory workerAdapterFactory) {
        this.workerAdapterFactory = workerAdapterFactory;
    }

    @Override
    public void setHandlerFactory(HandlerFactory handlerFactory) {
        this.handlerFactory = handlerFactory;
    }

    @Override
    public void setMapping(HandlerMapping mapping) {
        this.mapping = mapping;
    }

    @Override
    public void setAsynchronousPoolFactory(AsynchronousPoolFactory asynchronousPoolFactory) {
        this.asynchronousPoolFactory = asynchronousPoolFactory;
    }

    @Override
    public void setContext(EventContext context) {
        this.context = context;
    }

    @Override
    public void registerPluginChain(PluginChain pluginChain) {
        this.pluginChain = pluginChain;
    }

    @Override
    public HandlerMapping getMapping() {
        return this.mapping;
    }

    public void setPluginChain(PluginChain pluginChain) {
        this.pluginChain = pluginChain;
    }

    protected PluginChain getPluginChain() {
        return pluginChain;
    }

    protected AsynchronousPoolFactory getAsynchronousPoolFactory() {
        return asynchronousPoolFactory;
    }

    protected EventContext getContext() {
        return context;
    }

    protected WorkerAdapterFactory getWorkerAdapterFactory() {
        return workerAdapterFactory;
    }

    protected HandlerFactory getHandlerFactory() {
        return handlerFactory;
    }

    @Override
    public State state() {
        return state;
    }

}
