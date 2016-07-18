package org.cheetah.fighter.engine;

import org.cheetah.fighter.async.AsynchronousPoolFactory;
import org.cheetah.fighter.async.future.AsyncForeseeableWorkerFactory;
import org.cheetah.fighter.async.future.ForeseeableWorkerPoolFactory;
import org.cheetah.fighter.core.FighterConfig;
import org.cheetah.fighter.core.HandlerMapping;
import org.cheetah.fighter.core.engine.EngineBuilder;
import org.cheetah.fighter.core.governor.GovernorFactory;
import org.cheetah.fighter.core.handler.HandlerFactory;
import org.cheetah.fighter.core.worker.WorkerFactory;
import org.cheetah.fighter.governor.ForeseeableGovernorFactory;
import org.cheetah.fighter.handler.DomainEventHandlerFactory;
import org.cheetah.fighter.mapping.EventHandlerMapping;
import org.cheetah.fighter.worker.ForeseeableWorkerFactory;

/**
 * Created by Max on 2016/2/19.
 */
public class ForeseeableEngineBuilder implements EngineBuilder {

    @Override
    public HandlerFactory buildHandlerFactory() {
        return DomainEventHandlerFactory.getDomainEventHandlerFactory();
    }

    @Override
    public GovernorFactory buildGovernorFactory() {
        return new ForeseeableGovernorFactory();
    }

    @Override
    public WorkerFactory buildWorkerFactory() {
        return new ForeseeableWorkerFactory();
    }

    @Override
    public HandlerMapping buildMapping() {
        return EventHandlerMapping.getGenericMapping();
    }

    @Override
    public AsynchronousPoolFactory buildAsynchronousPoolFactory(FighterConfig fighterConfig) {
        AsyncForeseeableWorkerFactory foreseeableWorkerFactory = new AsyncForeseeableWorkerFactory();
        foreseeableWorkerFactory.setWorkerFactory(new ForeseeableWorkerFactory());
        ForeseeableWorkerPoolFactory factory = new ForeseeableWorkerPoolFactory();
        factory.setAsynchronousFactory(foreseeableWorkerFactory);
        return factory;
    }

}
