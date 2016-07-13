package org.cheetah.fighter.engine;

import org.cheetah.fighter.async.AsynchronousFactory;
import org.cheetah.fighter.async.AsynchronousPoolFactory;
import org.cheetah.fighter.async.ordinary.AsyncOrdinaryWorkerFactory;
import org.cheetah.fighter.async.ordinary.OrdinaryWorkerPoolFactory;
import org.cheetah.fighter.core.FighterConfig;
import org.cheetah.fighter.core.HandlerMapping;
import org.cheetah.fighter.core.engine.EngineBuilder;
import org.cheetah.fighter.core.governor.GovernorFactory;
import org.cheetah.fighter.core.handler.HandlerFactory;
import org.cheetah.fighter.core.worker.WorkerFactory;
import org.cheetah.fighter.governor.OrdinaryGovernorFactory;
import org.cheetah.fighter.handler.GenericHandlerFactory;
import org.cheetah.fighter.mapping.EventHandlerMapping;
import org.cheetah.fighter.worker.OrdinaryWorkerFactory;

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
    public AsynchronousPoolFactory buildAsynchronousPoolFactory(FighterConfig fighterConfig) {
        AsynchronousFactory ordinaryWorkerFactory = new AsyncOrdinaryWorkerFactory();
        OrdinaryWorkerPoolFactory factory = new OrdinaryWorkerPoolFactory();
        factory.setAsynchronousFactory(ordinaryWorkerFactory);
        return factory;
    }

}
