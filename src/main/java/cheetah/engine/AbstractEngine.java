package cheetah.engine;

import cheetah.async.AsynchronousPoolFactory;
import cheetah.common.logger.Debug;
import cheetah.governor.Governor;
import cheetah.governor.GovernorFactory;
import cheetah.handler.EventContext;
import cheetah.handler.Handler;
import cheetah.handler.HandlerFactory;
import cheetah.mapping.HandlerMapping;
import cheetah.plugin.InterceptorChain;
import cheetah.worker.Worker;
import cheetah.worker.WorkerFactory;

/**
 * Created by Max on 2016/3/2.
 */
public abstract class AbstractEngine implements Engine {
    private WorkerFactory workerFactory;
    private HandlerFactory handlerFactory;
    private GovernorFactory governorFactory;
    private InterceptorChain interceptorChain;
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
        interceptorChain = null;
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

    public void setGovernor(Governor governor) {
        this.governor = governor;
    }

    @Override
    public HandlerMapping getMapping() {
        return this.mapping;
    }

    public void setInterceptorChain(InterceptorChain interceptorChain) {
        this.interceptorChain = interceptorChain;
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

    protected InterceptorChain interceptorChain() {
        return interceptorChain;
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
