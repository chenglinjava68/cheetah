package cheetah.engine.support;

import cheetah.async.AsynchronousFactory;
import cheetah.async.AsynchronousPoolFactory;
import cheetah.async.ordinary.AsyncOrdinaryWorkerFactory;
import cheetah.async.ordinary.OrdinaryWorkerPoolFactory;
import cheetah.core.Configuration;
import cheetah.engine.EngineBuilder;
import cheetah.governor.GovernorFactory;
import cheetah.governor.support.OrdinaryGovernorFactory;
import cheetah.handler.HandlerFactory;
import cheetah.handler.support.GenericHandlerFactory;
import cheetah.mapper.Mapper;
import cheetah.mapper.support.HandlerMapper;
import cheetah.worker.WorkerFactory;
import cheetah.worker.support.OrdinaryWorkerFactory;

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
    public Mapper buildMapper() {
        return HandlerMapper.getGenericMapper();
    }

    @Override
    public AsynchronousPoolFactory buildAsynchronousPoolFactory(Configuration configuration) {
        AsynchronousFactory ordinaryWorkerFactory = new AsyncOrdinaryWorkerFactory();
        OrdinaryWorkerPoolFactory factory = new OrdinaryWorkerPoolFactory();
        factory.setAsynchronousFactory(ordinaryWorkerFactory);
        return factory;
    }

}
