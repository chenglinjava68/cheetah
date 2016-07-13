package org.cheetah.fighter.engine;

import org.cheetah.fighter.async.AsynchronousPoolFactory;
import org.cheetah.fighter.async.disruptor.DisruptorFactory;
import org.cheetah.fighter.async.disruptor.DisruptorPoolFactory;
import org.cheetah.fighter.core.FighterConfig;
import org.cheetah.fighter.core.HandlerMapping;
import org.cheetah.fighter.core.eventbus.EngineBuilder;
import org.cheetah.fighter.core.governor.GovernorFactory;
import org.cheetah.fighter.core.handler.HandlerFactory;
import org.cheetah.fighter.core.worker.WorkerFactory;
import org.cheetah.fighter.governor.DisruptorGovernorFactory;
import org.cheetah.fighter.handler.GenericHandlerFactory;
import org.cheetah.fighter.mapping.EventHandlerMapping;
import org.cheetah.fighter.worker.DisruptorWorkerFactory;

/**
 * Created by Max on 2016/3/2.
 */
public class DisruptorEngineBuilder implements EngineBuilder {
    @Override
    public HandlerFactory buildHandlerFactory() {
        return GenericHandlerFactory.getGenericHandlerFactory();
    }

    @Override
    public GovernorFactory buildGovernorFactory() {
        return new DisruptorGovernorFactory();
    }

    @Override
    public WorkerFactory buildWorkerFactory() {
        return new DisruptorWorkerFactory();
    }

    @Override
    public HandlerMapping buildMapping() {
        return EventHandlerMapping.getGenericMapping();
    }

    @Override
    public AsynchronousPoolFactory buildAsynchronousPoolFactory(FighterConfig fighterConfig) {
        DisruptorFactory disruptorFactory = new DisruptorFactory();
        if(fighterConfig.ringBufferSize() > 0)
            disruptorFactory.setRingbufferSize(fighterConfig.ringBufferSize());
        if(fighterConfig.maxThreads() > 0 && fighterConfig.minThreads() > 0) {
            disruptorFactory.setMinThreads(fighterConfig.minThreads());
            disruptorFactory.setMaxThreads(fighterConfig.maxThreads());
        }
        DisruptorPoolFactory poolFactory = new DisruptorPoolFactory();
        poolFactory.setAsynchronousFactory(disruptorFactory);
        return poolFactory;
    }
}
