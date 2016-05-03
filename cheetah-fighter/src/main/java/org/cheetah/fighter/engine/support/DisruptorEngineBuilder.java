package org.cheetah.fighter.engine.support;

import org.cheetah.fighter.async.AsynchronousPoolFactory;
import org.cheetah.fighter.async.disruptor.DisruptorFactory;
import org.cheetah.fighter.async.disruptor.DisruptorPoolFactory;
import org.cheetah.fighter.core.Configuration;
import org.cheetah.fighter.engine.EngineBuilder;
import org.cheetah.fighter.mapping.HandlerMapping;
import org.cheetah.fighter.mapping.support.EventHandlerMapping;
import org.cheetah.fighter.worker.WorkerFactory;
import org.cheetah.fighter.governor.GovernorFactory;
import org.cheetah.fighter.governor.support.DisruptorGovernorFactory;
import org.cheetah.fighter.handler.HandlerFactory;
import org.cheetah.fighter.handler.support.GenericHandlerFactory;
import org.cheetah.fighter.worker.support.DisruptorWorkerFactory;

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
    public AsynchronousPoolFactory buildAsynchronousPoolFactory(Configuration configuration) {
        DisruptorFactory disruptorFactory = new DisruptorFactory();
        if(configuration.ringBufferSize() > 0)
            disruptorFactory.setRingbufferSize(configuration.ringBufferSize());
        if(configuration.maxThreads() > 0 && configuration.minThreads() > 0) {
            disruptorFactory.setMinThreads(configuration.minThreads());
            disruptorFactory.setMaxThreads(configuration.maxThreads());
        }
        DisruptorPoolFactory poolFactory = new DisruptorPoolFactory();
        poolFactory.setAsynchronousFactory(disruptorFactory);
        return poolFactory;
    }
}
