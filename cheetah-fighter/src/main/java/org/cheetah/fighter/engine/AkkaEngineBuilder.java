package org.cheetah.fighter.engine;

import akka.actor.ActorRef;
import org.cheetah.fighter.async.AsynchronousPoolFactory;
import org.cheetah.fighter.async.akka.ActorFactory;
import org.cheetah.fighter.async.akka.ActorPoolFactory;
import org.cheetah.fighter.core.FighterConfig;
import org.cheetah.fighter.core.HandlerMapping;
import org.cheetah.fighter.core.engine.EngineBuilder;
import org.cheetah.fighter.core.governor.GovernorFactory;
import org.cheetah.fighter.core.handler.HandlerFactory;
import org.cheetah.fighter.core.worker.WorkerFactory;
import org.cheetah.fighter.governor.AkkaGovernorFactory;
import org.cheetah.fighter.handler.DomainEventHandlerFactory;
import org.cheetah.fighter.mapping.EventHandlerMapping;
import org.cheetah.fighter.worker.AkkaWorkerFactory;

/**
 * Created by Max on 2016/2/19.
 */
public class AkkaEngineBuilder implements EngineBuilder {

    @Override
    public HandlerFactory buildHandlerFactory() {
        return DomainEventHandlerFactory.getDomainEventHandlerFactory();
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
    public AsynchronousPoolFactory buildAsynchronousPoolFactory(FighterConfig fighterConfig) {
        ActorFactory actorFactory = new ActorFactory();
        if(fighterConfig.eventPerformerSize() > 0)
            actorFactory.setActorSize(fighterConfig.eventPerformerSize());
        AsynchronousPoolFactory<ActorRef> factory = new ActorPoolFactory();
        factory.setAsynchronousFactory(actorFactory);
        return factory;
    }

}
