package org.cheetah.fighter.engine.support;

import akka.actor.ActorRef;
import org.cheetah.fighter.async.AsynchronousPoolFactory;
import org.cheetah.fighter.async.akka.ActorFactory;
import org.cheetah.fighter.async.akka.ActorPoolFactory;
import org.cheetah.fighter.core.Configuration;
import org.cheetah.fighter.engine.EngineBuilder;
import org.cheetah.fighter.mapping.HandlerMapping;
import org.cheetah.fighter.mapping.support.EventHandlerMapping;
import org.cheetah.fighter.worker.WorkerFactory;
import org.cheetah.fighter.worker.support.AkkaWorkerFactory;
import org.cheetah.fighter.governor.GovernorFactory;
import org.cheetah.fighter.governor.support.AkkaGovernorFactory;
import org.cheetah.fighter.handler.HandlerFactory;
import org.cheetah.fighter.handler.support.GenericHandlerFactory;

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
