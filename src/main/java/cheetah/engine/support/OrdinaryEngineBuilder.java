package cheetah.engine.support;

import cheetah.core.async.AsynchronousFactory;
import cheetah.core.async.AsynchronousPoolFactory;
import cheetah.core.async.ordinary.AsyncOrdinaryWorkerFactory;
import cheetah.core.async.ordinary.OrdinaryWorkerPoolFactory;
import cheetah.core.Configuration;
import cheetah.engine.EngineBuilder;
import cheetah.governor.GovernorFactory;
import cheetah.governor.support.OrdinaryGovernorFactory;
import cheetah.handler.HandlerFactory;
import cheetah.handler.support.GenericHandlerFactory;
import cheetah.mapping.HandlerMapping;
import cheetah.mapping.support.EventHandlerMapping;
import cheetah.worker.WorkerFactory;
import cheetah.worker.support.OrdinaryWorkerFactory;

/**
 * Created by Max on 2016/2/19.
 */
public class OrdinaryEngineBuilder implements EngineBuilder {

    @Override
    public HandlerFactory buildHandlerFactory() {
        return GenericHandlerFactory.getGenericHandlerFactory();
    }

    @Override
    public GovernorFactory buildGovernorFactory() {
        return new OrdinaryGovernorFactory();
    }

    @Override
    public WorkerFactory buildWorkerFactory() {
        return new OrdinaryWorkerFactory();
    }

    @Override
    public HandlerMapping buildMapping() {
        return EventHandlerMapping.getGenericMapping();
    }

    @Override
    public AsynchronousPoolFactory buildAsynchronousPoolFactory(Configuration configuration) {
        AsynchronousFactory ordinaryWorkerFactory = new AsyncOrdinaryWorkerFactory();
        OrdinaryWorkerPoolFactory factory = new OrdinaryWorkerPoolFactory();
        factory.setAsynchronousFactory(ordinaryWorkerFactory);
        return factory;
    }

}
