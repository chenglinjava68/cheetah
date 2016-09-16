package org.cheetah.fighter.engine.support;

import akka.actor.ActorRef;
import org.cheetah.fighter.FighterConfig;
import org.cheetah.fighter.async.AsynchronousPoolFactory;
import org.cheetah.fighter.async.akka.ActorFactory;
import org.cheetah.fighter.async.akka.ActorPoolFactory;
import org.cheetah.fighter.engine.EngineBuilder;
import org.cheetah.fighter.handler.HandlerFactory;
import org.cheetah.fighter.handler.support.DomainEventHandlerFactory;
import org.cheetah.fighter.worker.WorkerAdapterFactory;

/**
 * Created by Max on 2016/2/19.
 */
public class AkkaEngineBuilder implements EngineBuilder {

    @Override
    public HandlerFactory buildHandlerFactory() {
        return DomainEventHandlerFactory.getDomainEventHandlerFactory();
    }

    @Override
    public WorkerAdapterFactory buildWorkerAdapterFactory() {
        return null;
    }

    @Override
    public AsynchronousPoolFactory buildAsynchronousPoolFactory(FighterConfig fighterConfig) {
        ActorFactory actorFactory = new ActorFactory();
        if(fighterConfig.getEventPerformerSize() > 0)
            actorFactory.setActorSize(fighterConfig.getEventPerformerSize());
        AsynchronousPoolFactory<ActorRef> factory = new ActorPoolFactory();
        factory.setAsynchronousFactory(actorFactory);
        return factory;
    }

}
