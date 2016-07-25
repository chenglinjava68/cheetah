package org.cheetah.fighter.mapping;

import com.google.common.collect.Lists;
import org.cheetah.commons.utils.CollectionUtils;
import org.cheetah.fighter.*;
import org.cheetah.fighter.engine.Engine;
import org.cheetah.fighter.handler.Handler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * Created by Max on 2016/7/21.
 */
public class EventHandlerResolver {
    private Engine engine;
    private FighterConfig fighterConfig; //框架配置
    private final ReentrantLock lock = new ReentrantLock();

    public EventHandlerResolver(Engine engine, FighterConfig fighterConfig) {
        this.engine = engine;
        this.fighterConfig = fighterConfig;
    }

    public List<Handler> getHandlers(final DomainEvent event, final EventBus.HandlerMapperKey mapperKey) {
        return resolve(event, mapperKey);
    }

    /**
     * Mapper helper
     *
     * @param event
     */
    private List<Handler> resolve(final DomainEvent event, final EventBus.HandlerMapperKey mapperKey) {
        lock.lock();
        try {
            if (DomainEvent.class.isAssignableFrom(event.getClass())) {
                List<DomainEventListener> eventListeners = supportsSmartListener(event);
                if (eventListeners.isEmpty()) {
                    eventListeners = supportsUniversalListener(event);
                }
                return assembleEventHandlerMapping(mapperKey, eventListeners);
            }
        } finally {
            lock.unlock();
        }
        return Collections.emptyList();
    }

    @SuppressWarnings("unchecked")
    private List<DomainEventListener> supportsUniversalListener(final Event event) {
        List<DomainEventListener> eventListeners = this.fighterConfig.getEventListeners();

        return eventListeners.stream().filter(o ->{
            List classes = CollectionUtils.arrayToList(o.getClass().getInterfaces());
            Optional<Class> classOptional = classes.stream().filter(it -> it.equals(DomainEventListener.class)).findFirst();
            if(!classOptional.isPresent())
                return false;
            Type type = o.getClass().getGenericInterfaces()[0];
            if(!(type instanceof ParameterizedType))
                return true;
            Type[] parameterizedType = ((ParameterizedType)type).getActualTypeArguments();
            if(parameterizedType.length < 1)
                return true;
            return event.getClass().equals(parameterizedType[0]);
        }).collect(Collectors.toList());
    }

    private List<Handler> assembleEventHandlerMapping(EventBus.HandlerMapperKey mapperKey,
                                                      List<DomainEventListener> listeners) {
        List<Handler> handlers = Lists.newArrayList();
        for (DomainEventListener listener : listeners) {
            Handler handler = engine.assignDomainEventHandler();
            handler.registerEventListener(listener);
            handlers.add(handler);
        }

        engine.getMapping().put(mapperKey, handlers);
        return handlers;
    }

    /**
     * 根据domainevent过滤出相应的listener
     * @param event
     * @return
     */
    private List<DomainEventListener> supportsSmartListener(final DomainEvent event) {
        List<DomainEventListener> list = this.fighterConfig.getEventListeners();

        return list.stream().filter(o -> SmartDomainEventListener.class.isAssignableFrom(o.getClass()))
                .map(o -> ((SmartDomainEventListener) o))
                .filter(o -> o.supportsEventType(event.getClass()))
                .filter(o -> o.supportsSourceType(event.getSource().getClass()))
                .collect(Collectors.toList());
    }
}
