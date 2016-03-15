package cheetah.fighter.engine.support;

import cheetah.fighter.async.AsynchronousPoolFactory;
import cheetah.fighter.async.disruptor.DisruptorFactory;
import cheetah.fighter.async.disruptor.DisruptorPoolFactory;
import cheetah.fighter.core.Configuration;
import cheetah.fighter.engine.EngineBuilder;
import cheetah.fighter.mapping.HandlerMapping;
import cheetah.fighter.mapping.support.EventHandlerMapping;
import cheetah.fighter.worker.WorkerFactory;
import cheetah.fighter.governor.GovernorFactory;
import cheetah.fighter.governor.support.DisruptorGovernorFactory;
import cheetah.fighter.handler.HandlerFactory;
import cheetah.fighter.handler.support.GenericHandlerFactory;
import cheetah.fighter.worker.support.DisruptorWorkerFactory;

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
        DisruptorPoolFactory poolFactory = new DisruptorPoolFactory();
        poolFactory.setAsynchronousFactory(disruptorFactory);
        return poolFactory;
    }
}
