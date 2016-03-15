package cheetah.fighter.engine.support;

import akka.actor.ActorRef;
import cheetah.fighter.async.AsynchronousPoolFactory;
import cheetah.fighter.async.akka.ActorFactory;
import cheetah.fighter.async.akka.ActorPoolFactory;
import cheetah.fighter.core.Configuration;
import cheetah.fighter.engine.EngineBuilder;
import cheetah.fighter.mapping.HandlerMapping;
import cheetah.fighter.mapping.support.EventHandlerMapping;
import cheetah.fighter.worker.WorkerFactory;
import cheetah.fighter.worker.support.AkkaWorkerFactory;
import cheetah.fighter.governor.GovernorFactory;
import cheetah.fighter.governor.support.AkkaGovernorFactory;
import cheetah.fighter.handler.HandlerFactory;
import cheetah.fighter.handler.support.GenericHandlerFactory;

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
        if(configuration.eventPerformerSize() > 0)
            actorFactory.setActorSize(configuration.eventPerformerSize());
        AsynchronousPoolFactory<ActorRef> factory = new ActorPoolFactory();
        factory.setAsynchronousFactory(actorFactory);
        return factory;
    }

}
