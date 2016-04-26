package org.cheetah.fighter.engine.support;

import org.cheetah.fighter.async.AsynchronousFactory;
import org.cheetah.fighter.async.AsynchronousPoolFactory;
import org.cheetah.fighter.async.ordinary.AsyncOrdinaryWorkerFactory;
import org.cheetah.fighter.async.ordinary.OrdinaryWorkerPoolFactory;
import org.cheetah.fighter.core.Configuration;
import org.cheetah.fighter.engine.EngineBuilder;
import org.cheetah.fighter.governor.GovernorFactory;
import org.cheetah.fighter.governor.support.OrdinaryGovernorFactory;
import org.cheetah.fighter.handler.HandlerFactory;
import org.cheetah.fighter.handler.support.GenericHandlerFactory;
import org.cheetah.fighter.mapping.HandlerMapping;
import org.cheetah.fighter.mapping.support.EventHandlerMapping;
import org.cheetah.fighter.worker.WorkerFactory;
import org.cheetah.fighter.worker.support.OrdinaryWorkerFactory;

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
