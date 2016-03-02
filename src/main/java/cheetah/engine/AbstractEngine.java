package cheetah.engine;

import akka.actor.ActorRef;
import cheetah.async.AsynchronousPoolFactory;
import cheetah.common.logger.Debug;
import cheetah.core.EventContext;
import cheetah.governor.Governor;
import cheetah.governor.GovernorFactory;
import cheetah.handler.Handler;
import cheetah.handler.HandlerFactory;
import cheetah.handler.support.GenericHandlerFactory;
import cheetah.mapper.Mapper;
import cheetah.plugin.InterceptorChain;
import cheetah.worker.Worker;
import cheetah.worker.WorkerFactory;
import cheetah.worker.support.AkkaWorkerFactory;

import java.util.Objects;

/**
 * Created by Max on 2016/3/2.
 */
public abstract class AbstractEngine implements Engine {
    private WorkerFactory workerFactory;
    private HandlerFactory handlerFactory;
    private GovernorFactory governorFactory;
    private InterceptorChain interceptorChain;
    private AsynchronousPoolFactory<ActorRef> asynchronousPoolFactory;
    private volatile Mapper mapper;
    private volatile Governor governor;
    private EventContext context;
    protected State state;

    @Override
    public void start() {
        Debug.log(this.getClass(), "DefaultEngine start ...");
        initialize();
        asynchronousPoolFactory().setEventContext(context);
        if (Objects.isNull(workerFactory()))
            workerFactory = new AkkaWorkerFactory();
        if (Objects.isNull(handlerFactory))
            handlerFactory = new GenericHandlerFactory();
        this.state = State.RUNNING;
    }

    protected abstract void initialize();

    @Override
    public void stop() {
        asynchronousPoolFactory.stop();
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
    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
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
    public Mapper getMapper() {
        return this.mapper;
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

    protected Mapper mapper() {
        return mapper;
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
