package org.cheetah.fighter.engine.support;

import org.cheetah.commons.utils.StringUtils;
import org.cheetah.fighter.FighterConfig;
import org.cheetah.fighter.async.AsynchronousPoolFactory;
import org.cheetah.fighter.async.disruptor.DisruptorFactory;
import org.cheetah.fighter.async.disruptor.DisruptorPoolFactory;
import org.cheetah.fighter.engine.EngineBuilder;
import org.cheetah.fighter.handler.HandlerFactory;
import org.cheetah.fighter.handler.support.DomainEventHandlerFactory;
import org.cheetah.fighter.worker.WorkerAdapterFactory;
import org.cheetah.fighter.worker.support.DisruptorWorkerAdapterFactory;
import org.cheetah.fighter.worker.support.DisruptorWorkerFactory;

/**
 * Created by Max on 2016/3/2.
 */
public class DisruptorEngineBuilder implements EngineBuilder {
    @Override
    public HandlerFactory buildHandlerFactory() {
        return DomainEventHandlerFactory.getDomainEventHandlerFactory();
    }

    @Override
    public WorkerAdapterFactory buildWorkerAdapterFactory() {
        return new DisruptorWorkerAdapterFactory();
    }

    @Override
    public AsynchronousPoolFactory buildAsynchronousPoolFactory(FighterConfig fighterConfig) {
        DisruptorFactory disruptorFactory = new DisruptorFactory();
        if(fighterConfig.getQueueLength() > 0)
            disruptorFactory.setRingbufferSize(fighterConfig.getQueueLength());
        if(fighterConfig.getMaxThreads() > 0 && fighterConfig.getMinThreads() > 0) {
            disruptorFactory.setMinThreahs(fighterConfig.getMinThreads());
            disruptorFactory.setMaxThreahs(fighterConfig.getMaxThreads());
        }
        if(fighterConfig.getRingBuffer() > 0)
            disruptorFactory.setRingbufferSize(fighterConfig.getRingBuffer());
        if(StringUtils.isNotBlank(fighterConfig.getRejectionPolicy()))
            disruptorFactory.setRejectionPolicy(fighterConfig.getRejectionPolicy());
        disruptorFactory.setWorkerFactory(new DisruptorWorkerFactory());
        DisruptorPoolFactory poolFactory = new DisruptorPoolFactory();
        poolFactory.setAsynchronousFactory(disruptorFactory);
        return poolFactory;
    }
}
