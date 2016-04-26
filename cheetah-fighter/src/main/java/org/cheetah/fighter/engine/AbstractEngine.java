package org.cheetah.fighter.engine;

import org.cheetah.fighter.async.AsynchronousPoolFactory;
import org.cheetah.commons.logger.Debug;
import org.cheetah.fighter.core.EventContext;
import org.cheetah.fighter.plugin.PluginChain;
import org.cheetah.fighter.handler.Handler;
import org.cheetah.fighter.mapping.HandlerMapping;
import org.cheetah.fighter.worker.Worker;
import org.cheetah.fighter.worker.WorkerFactory;
import org.cheetah.fighter.governor.Governor;
import org.cheetah.fighter.governor.GovernorFactory;
import org.cheetah.fighter.handler.HandlerFactory;

/**
 * Created by Max on 2016/3/2.
 */
public abstract class AbstractEngine implements Engine {
    private WorkerFactory workerFactory;
    private HandlerFactory handlerFactory;
    private GovernorFactory governorFactory;
    private PluginChain pluginChain = new PluginChain();
    private AsynchronousPoolFactory asynchronousPoolFactory;
    private volatile HandlerMapping mapping;
    private Governor governor;
    private EventContext context;
    protected State state;

    @Override
    public void start() {
        Debug.log(this.getClass(), "DefaultEngine start ...");
        initialize();
        this.state = State.RUNNING;
    }

    public void initialize() {
        this.asynchronousPoolFactory.setEventContext(this.context);
        asynchronousPoolFactory().start();
    }

    @Override
    public void stop() {
        workerFactory = null;
        handlerFactory = null;
        governorFactory = null;
        pluginChain = null;
        asynchronousPoolFactory.stop();
        asynchronousPoolFactory = null;
        Debug.log(this.getClass(), "DefualtEngine has been shut down.");
        this.state = State.STOP;
    }

    @Override
    public Handler assignApplicationEventHandler() {
        return handlerFactory.createApplicationEventHandler();
    }

    @Override
    public Handler assignDomainEventHandler() {
        return handlerFactory.createDomainEventHandler();
    }

    @Override
    public Worker assignWorker() {
        return workerFactory.createWorker();
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

    public void setGovernor(Governor governor) {
        this.governor = governor;
    }

    @Override
    public HandlerMapping getMapping() {
        return this.mapping;
    }

    public void setPluginChain(PluginChain interceptorChain) {
        this.pluginChain = interceptorChain;
    }

    protected WorkerFactory workerFactory() {
        return workerFactory;
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

    protected Governor governor() {
        return governor;
    }

    protected EventContext context() {
        return context;
    }

    @Override
    public State state() {
        return state;
    }

}
