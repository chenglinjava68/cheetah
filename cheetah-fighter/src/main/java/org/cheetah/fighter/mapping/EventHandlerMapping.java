package org.cheetah.fighter.mapping;

import org.cheetah.fighter.EventBus;
import org.cheetah.fighter.HandlerMapping;
import org.cheetah.fighter.handler.Handler;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 机器映射器
 * 在调度中心接受到一个事件后，调度会将其封装为一个Handler， 将其存到本映射器中。
 * 调度中每次都会检查映射器中有没有对应的工作机器，没有则创建，有则直接获取，不会每次都会创建
 * Created by Max on 2016/2/23.
 */
@Deprecated
public class EventHandlerMapping implements HandlerMapping {
    private Map<EventBus.HandlerMapperKey, List<Handler>> handlerMapper = new ConcurrentHashMap<>();
    private static final HandlerMapping genericMapping = new EventHandlerMapping();

    public static HandlerMapping getGenericMapping() {
        return genericMapping;
    }

    @Override
    public List<Handler> getHandlers(EventBus.HandlerMapperKey mapperKey) {
        return isExists(mapperKey) ? new ArrayList<>(handlerMapper.get(mapperKey)) : Collections.emptyList();
    }

    @Override
    public void put(EventBus.HandlerMapperKey mapperKey, List<Handler> handlers) {
        if (handlers.isEmpty()) {
            return;
        }
        handlerMapper.putIfAbsent(mapperKey, handlers);
    }

    @Override
    public Set<EventBus.HandlerMapperKey> mapperKeys() {
        return handlerMapper.keySet();
    }

    @Override
    public boolean isExists(EventBus.HandlerMapperKey mapperKey) {
        return handlerMapper.containsKey(mapperKey);
    }
}
