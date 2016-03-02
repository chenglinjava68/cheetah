package cheetah.async.akka;

import akka.actor.ActorRef;
import cheetah.async.AsynchronousFactory;
import cheetah.async.AsynchronousPoolFactory;
import cheetah.core.EventContext;
import cheetah.core.NoMapperException;
import cheetah.event.Event;
import cheetah.handler.Handler;
import cheetah.mapper.Mapper;

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
    private ConcurrentHashMap<Mapper.HandlerMapperKey, ActorRef> actorPool = new ConcurrentHashMap<>();
    private EventContext context;

    public ActorPoolFactory() {
        this.actorFactory = new ActorFactory();
    }

    public ActorRef createActor() {
        Event event = context.getEventMessage().event();
        String name = event.getClass().getName();
        Mapper.HandlerMapperKey mapperKey = Mapper.HandlerMapperKey.generate(event);
        if (actorPool.containsKey(mapperKey))
            return this.actorPool.get(mapperKey);
        Map<Class<? extends EventListener>, Handler> handlerMap = getMapperFrom();
        return this.actorFactory.createAsynchronous(name, handlerMap);
    }

    @Override
    public ActorRef getAsynchronous() {
        ActorRef actor = actorPool.get(Mapper.HandlerMapperKey.generate(context.getEventMessage().event()));
        if (Objects.nonNull(actor))
            return actor;
        else {
            synchronized (this) {
                if (this.context.getHandlers().isEmpty())
                    throw new NoMapperException();
                actor = createActor();
                Mapper.HandlerMapperKey mapperKey = Mapper.HandlerMapperKey.generate(context.getEventMessage().event());
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
        Map<Class<? extends EventListener>, Handler> handlerMap = context.getHandlers();
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
