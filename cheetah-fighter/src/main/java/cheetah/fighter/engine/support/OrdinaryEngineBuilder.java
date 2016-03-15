package cheetah.fighter.engine.support;

import cheetah.fighter.async.AsynchronousFactory;
import cheetah.fighter.async.AsynchronousPoolFactory;
import cheetah.fighter.async.ordinary.AsyncOrdinaryWorkerFactory;
import cheetah.fighter.async.ordinary.OrdinaryWorkerPoolFactory;
import cheetah.fighter.core.Configuration;
import cheetah.fighter.engine.EngineBuilder;
import cheetah.fighter.governor.GovernorFactory;
import cheetah.fighter.governor.support.OrdinaryGovernorFactory;
import cheetah.fighter.handler.HandlerFactory;
import cheetah.fighter.handler.support.GenericHandlerFactory;
import cheetah.fighter.mapping.HandlerMapping;
import cheetah.fighter.mapping.support.EventHandlerMapping;
import cheetah.fighter.worker.WorkerFactory;
import cheetah.fighter.worker.support.OrdinaryWorkerFactory;

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
