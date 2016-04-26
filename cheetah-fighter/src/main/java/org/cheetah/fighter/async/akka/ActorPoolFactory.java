package org.cheetah.fighter.async.akka;

import akka.actor.ActorRef;
import org.cheetah.fighter.async.AsynchronousFactory;
import org.cheetah.fighter.async.AsynchronousPoolFactory;
import org.cheetah.fighter.core.EventContext;
import org.cheetah.fighter.core.NoMapperException;
import org.cheetah.fighter.event.Event;
import org.cheetah.fighter.mapping.HandlerMapping;
import org.cheetah.fighter.handler.Handler;

import java.util.EventListener;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * actor池的工厂
 * Created by Max on 2016/2/29.
 */
public class ActorPoolFactory implements AsynchronousPoolFactory<ActorRef> {

    private AsynchronousFactory<ActorRef> actorFactory;
    private ConcurrentHashMap<HandlerMapping.HandlerMapperKey, ActorRef> actorPool = new ConcurrentHashMap<>();
    private EventContext context;

    public ActorPoolFactory() {
        this.actorFactory = new ActorFactory();
    }

    public ActorRef createActor() {
        Event event = context.eventMessage().event();
        String name = event.getClass().getName();
        HandlerMapping.HandlerMapperKey mapperKey = HandlerMapping.HandlerMapperKey.generate(event);
        if (actorPool.containsKey(mapperKey))
            return this.actorPool.get(mapperKey);
        Map<Class<? extends EventListener>, Handler> handlerMap = getMapperFrom();
        return this.actorFactory.createAsynchronous(name, handlerMap, context.interceptors());
    }

    @Override
    public ActorRef getAsynchronous() {
        ActorRef actor = actorPool.get(HandlerMapping.HandlerMapperKey.generate(context.eventMessage().event()));
        if (Objects.nonNull(actor))
            return actor;
        else {
            synchronized (this) {
                if (this.context.handlers().isEmpty())
                    throw new NoMapperException();
                actor = createActor();
                HandlerMapping.HandlerMapperKey mapperKey = HandlerMapping.HandlerMapperKey.generate(context.eventMessage().event());
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

    private Map<Class<? extends EventListener>, Handler> getMapperFrom() {
        Map<Class<? extends EventListener>, Handler> handlerMap = context.handlers();
        if (handlerMap.isEmpty())
            throw new NoMapperException();
        return handlerMap;
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
