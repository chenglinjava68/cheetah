package cheetah.engine.support;

import cheetah.async.AsynchronousPoolFactory;
import cheetah.async.disruptor.DisruptorFactory;
import cheetah.async.disruptor.DisruptorPoolFactory;
import cheetah.core.Configuration;
import cheetah.engine.EngineBuilder;
import cheetah.governor.GovernorFactory;
import cheetah.core.DisruptorGovernorFactory;
import cheetah.handler.HandlerFactory;
import cheetah.handler.support.GenericHandlerFactory;
import cheetah.mapper.Mapper;
import cheetah.mapper.support.HandlerMapper;
import cheetah.worker.WorkerFactory;
import cheetah.worker.support.DisruptorWorkerFactory;

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
    public Mapper buildMapper() {
        return HandlerMapper.getGenericMapper();
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
