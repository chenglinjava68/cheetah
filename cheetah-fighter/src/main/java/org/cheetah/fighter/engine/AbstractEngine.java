package org.cheetah.fighter.engine;

import org.cheetah.commons.logger.Info;
import org.cheetah.fighter.async.AsynchronousPoolFactory;
import org.cheetah.fighter.EventContext;
import org.cheetah.fighter.HandlerMapping;
import org.cheetah.fighter.governor.Governor;
import org.cheetah.fighter.governor.GovernorFactory;
import org.cheetah.fighter.handler.Handler;
import org.cheetah.fighter.handler.HandlerFactory;
import org.cheetah.fighter.plugin.PluginChain;
import org.cheetah.fighter.worker.Worker;
import org.cheetah.fighter.worker.WorkerFactory;

/**
 * Created by Max on 2016/3/2.
 */
public abstract class AbstractEngine<T> implements Engine<T> {
    private WorkerFactory workerFactory;
    private HandlerFactory handlerFactory;
    private GovernorFactory governorFactory;
    private PluginChain pluginChain = new PluginChain();
    private AsynchronousPoolFactory asynchronousPoolFactory;
    @Deprecated
    private volatile HandlerMapping mapping;
    private EventContext context;
    protected State state;

    @Override
    public void start() {
        Info.log(this.getClass(), "DefaultEngine start ...");
        initialize();
        this.state = State.RUNNING;
    }

    public void initialize() {
        this.asynchronousPoolFactory.setEventContext(this.context);
        asynchronousPoolFactory().start();
    }

    @Override
    public void stop() {
        handlerFactory = null;
        governorFactory = null;
        pluginChain = null;
        asynchronousPoolFactory.stop();
        asynchronousPoolFactory = null;
        Info.log(this.getClass(), "DefualtEngine has been shut down.");
        this.state = State.STOP;
    }

    @Override
    public boolean isRunning() {
        return state().equals(State.RUNNING);
    }

    @Override
    public Handler assignDomainEventHandler() {
        return handlerFactory.createDomainEventHandler();
    }

    @Override
    public WorkerFactory getWorkerFactory() {
        return this.workerFactory;
    }

    @Override
    public void setWorkerFactory(WorkerFactory workerFactory) {
        this.workerFactory = workerFactory;
    }

    @Override
    public void setHandlerFactory(HandlerFactory handlerFactory) {
        this.handlerFactory = handlerFactory;
    }

    @Override
    public void setGovernorFactory(GovernorFactory governorFactory) {
        this.governorFactory = governorFactory;
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

    public void setPluginChain(PluginChain interceptorChain) {
        this.pluginChain = interceptorChain;
    }

    protected HandlerFactory handlerFactory() {
        return handlerFactory;
    }

    protected GovernorFactory governorFactory() {
        return governorFactory;
    }

    protected PluginChain pluginChain() {
        return pluginChain;
    }

    protected AsynchronousPoolFactory asynchronousPoolFactory() {
        return asynchronousPoolFactory;
    }

    protected EventContext context() {
        return context;
    }

    @Override
    public State state() {
        return state;
    }

}
