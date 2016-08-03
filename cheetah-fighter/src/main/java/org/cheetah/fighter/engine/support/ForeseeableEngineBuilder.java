package org.cheetah.fighter.engine.support;


import org.cheetah.common.utils.StringUtils;
import org.cheetah.fighter.FighterConfig;
import org.cheetah.fighter.async.AsynchronousPoolFactory;
import org.cheetah.fighter.async.future.AsyncForeseeableWorkerFactory;
import org.cheetah.fighter.async.future.ForeseeableWorkerPoolFactory;
import org.cheetah.fighter.engine.EngineBuilder;
import org.cheetah.fighter.handler.HandlerFactory;
import org.cheetah.fighter.handler.support.DomainEventHandlerFactory;
import org.cheetah.fighter.worker.WorkerAdapterFactory;
import org.cheetah.fighter.worker.support.DisruptorWorkerAdapterFactory;
import org.cheetah.fighter.worker.support.ForeseeableWorkerFactory;

/**
 * Created by Max on 2016/2/19.
 */
public class ForeseeableEngineBuilder implements EngineBuilder {

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
        AsyncForeseeableWorkerFactory foreseeableWorkerFactory = new AsyncForeseeableWorkerFactory();
        if(fighterConfig.getQueueLength() > 0)
            foreseeableWorkerFactory.setQueueLength(fighterConfig.getQueueLength());
        if(fighterConfig.getMaxThreads() > 0 && fighterConfig.getMinThreads() > 0) {
            foreseeableWorkerFactory.setMinThreahs(fighterConfig.getMinThreads());
            foreseeableWorkerFactory.setMaxThreahs(fighterConfig.getMaxThreads());
        }
        if(StringUtils.isNotBlank(fighterConfig.getRejectionPolicy()))
            foreseeableWorkerFactory.setRejectionPolicy(fighterConfig.getRejectionPolicy());
        foreseeableWorkerFactory.setWorkerFactory(new ForeseeableWorkerFactory());
        ForeseeableWorkerPoolFactory factory = new ForeseeableWorkerPoolFactory();
        factory.setAsynchronousFactory(foreseeableWorkerFactory);
        return factory;
    }

}
