package org.cheetah.fighter.async.akka;

import akka.actor.ActorRef;
import org.cheetah.fighter.*;
import org.cheetah.fighter.async.AsynchronousFactory;
import org.cheetah.fighter.async.AsynchronousPoolFactory;
import org.cheetah.fighter.handler.Handler;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * actor池的工厂
 * Created by Max on 2016/2/29.
 */
public class ActorPoolFactory implements AsynchronousPoolFactory<ActorRef> {

    private AsynchronousFactory<ActorRef> actorFactory;
    private ConcurrentHashMap<EventBus.HandlerMapperKey, ActorRef> actorPool = new ConcurrentHashMap<>();
    private EventContext context;

    public ActorPoolFactory() {
        this.actorFactory = new ActorFactory();
    }

    public ActorRef createActor() {
        Event event = context.getEventMessage().event();
        String name = event.getClass().getName();
        EventBus.HandlerMapperKey mapperKey = EventBus.HandlerMapperKey.generate(event);
        if (actorPool.containsKey(mapperKey))
            return this.actorPool.get(mapperKey);
        List<Handler> handlers = getMapperFrom();
        return this.actorFactory.createAsynchronous(name, handlers, context.getInterceptors());
    }

    @Override
    public ActorRef getAsynchronous() {
        ActorRef actor = actorPool.get(EventBus.HandlerMapperKey.generate(context.getEventMessage().event()));
        if (Objects.nonNull(actor))
            return actor;
        else {
            synchronized (this) {
                if (this.context.getHandlers().isEmpty())
                    throw new NoMapperException();
                actor = createActor();
                EventBus.HandlerMapperKey mapperKey = EventBus.HandlerMapperKey.generate(context.getEventMessage().event());
                this.actorPool.putIfAbsent(mapperKey, actor);
                return actor;
            }
        }
    }

    @Override
    public void setEventContext(EventContext context) {
        this.context = context;
    }

    @Override
    public void setAsynchronousFactory(AsynchronousFactory asynchronousFactory) {
        this.actorFactory = asynchronousFactory;
    }

    private List<Handler> getMapperFrom() {
        List<Handler> handlers = context.getHandlers();
        if (handlers.isEmpty())
            throw new NoMapperException();
        return handlers;
    }

    @Override
    public void start() {
        actorFactory.start();
    }

    @Override
    public void stop() {
        for (ActorRef actor : this.actorPool.values()) {
            ((ActorFactory)actorFactory).actorSystem().stop(actor);
        }
        actorFactory.stop();
    }

}
