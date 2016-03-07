package cheetah.engine.support;

import akka.actor.ActorRef;
import cheetah.async.AsynchronousPoolFactory;
import cheetah.async.akka.ActorFactory;
import cheetah.async.akka.ActorPoolFactory;
import cheetah.core.Configuration;
import cheetah.engine.EngineBuilder;
import cheetah.governor.GovernorFactory;
import cheetah.governor.support.AkkaGovernorFactory;
import cheetah.handler.HandlerFactory;
import cheetah.handler.support.GenericHandlerFactory;
import cheetah.mapping.HandlerMapping;
import cheetah.mapping.support.EventHandlerMapping;
import cheetah.worker.WorkerFactory;
import cheetah.worker.support.AkkaWorkerFactory;

/**
 * Created by Max on 2016/2/19.
 */
public class AkkaEngineBuilder implements EngineBuilder {

    @Override
    public HandlerFactory buildHandlerFactory() {
        return GenericHandlerFactory.getGenericHandlerFactory();
    }

    @Override
    public GovernorFactory buildGovernorFactory() {
        return new AkkaGovernorFactory();
    }

    @Override
    public WorkerFactory buildWorkerFactory() {
        return new AkkaWorkerFactory();
    }

    @Override
    public HandlerMapping buildMapping() {
        return EventHandlerMapping.getGenericMapping();
    }

    @Override
    public AsynchronousPoolFactory buildAsynchronousPoolFactory(Configuration configuration) {
        ActorFactory actorFactory = new ActorFactory();
        if(configuration.getEventPerformerSize() > 0)
            actorFactory.setActorSize(configuration.getEventPerformerSize());
        AsynchronousPoolFactory<ActorRef> factory = new ActorPoolFactory();
        factory.setAsynchronousFactory(actorFactory);
        return factory;
    }

}
