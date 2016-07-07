package org.cheetah.fighter.core;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.cheetah.commons.Startable;
import org.cheetah.commons.logger.Info;
import org.cheetah.commons.utils.CollectionUtils;
import org.cheetah.commons.utils.ObjectUtils;
import org.cheetah.commons.utils.StringUtils;
import org.cheetah.fighter.engine.Engine;
import org.cheetah.fighter.engine.EngineDirector;
import org.cheetah.fighter.engine.support.EnginePolicy;
import org.cheetah.fighter.event.DomainEvent;
import org.cheetah.fighter.event.DomainEventListener;
import org.cheetah.fighter.event.Event;
import org.cheetah.fighter.event.SmartDomainEventListener;
import org.cheetah.fighter.handler.Handler;
import org.cheetah.fighter.mapping.HandlerMapping;
import org.cheetah.fighter.plugin.Plugin;
import org.cheetah.fighter.plugin.PluginChain;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import static com.google.common.collect.Collections2.filter;

/**
 * Created by Max on 2016/2/23.
 */
public abstract class AbstractDispatcher implements Dispatcher, Startable {

    private Configuration configuration; //框架配置
    private final PluginChain pluginChain = new PluginChain();
    private Engine engine;
    private EngineDirector engineDirector;
    private EnginePolicy enginePolicy;
    private final Map<InterceptorCacheKey, List<Interceptor>> interceptorCache = new ConcurrentHashMap<>();
    private final ReentrantLock lock = new ReentrantLock();
    private final EventContext context = EventContext.getContext();

    /**
     * 接收事件消息，安排相应的engine对其进行处理
     *
     * @param eventMessage
     * @return
     */
    @Override
    public EventResult receive(final EventMessage eventMessage) {
        Info.log(this.getClass(), "receive message: " + eventMessage);
        try {
            context().setEventMessage(eventMessage);
            Event event = eventMessage.event();
            HandlerMapping.HandlerMapperKey key = HandlerMapping.HandlerMapperKey.generate(event.getClass(), event.getSource().getClass());
            List<Interceptor> interceptors = findInterceptor(event);
            context.setInterceptor(interceptors);
            boolean exists = engine.getMapping().isExists(key);
            if (exists) {
                Map<Class<? extends EventListener>, Handler> handlerMap = engine.getMapping().getHandlers(key);
                context.setHandlers(handlerMap);
                return dispatch();
            }
            Map<Class<? extends EventListener>, Handler> handlerMap = convertMapper(event, key);
            context.setHandlers(handlerMap);
            return dispatch();
        } finally {
            context.removeEventMessage();
            context.removeHandlers();
            context.removeInterceptor();
        }
    }


    @Override
    public void start() {
        if (configuration != null) {
            if (configuration.hasPlugin())
                initializesPlugin(pluginChain);
        } else
            configuration = new Configuration();
        if (StringUtils.isEmpty(configuration.policy())) {
            enginePolicy = EnginePolicy.DISRUPTOR;
            engineDirector = enginePolicy.getEngineDirector();
        } else {
            enginePolicy = EnginePolicy.formatFrom(configuration.policy());
            engineDirector = enginePolicy.getEngineDirector();
        }
        engineDirector.setConfiguration(this.configuration);
        engine = engineDirector.directEngine();
        engine.setContext(this.context());
        engine.registerPluginChain(pluginChain);
        engine.start();
    }

    @Override
    public void stop() {
        this.engine.stop();
        this.engine = null;
        engineDirector = null;
        enginePolicy = null;
    }

    public PluginChain initializesPlugin(PluginChain chain) {
        Objects.requireNonNull(chain, "chain must not be null");
        for (Plugin plugin : configuration.plugins())
            chain.register(plugin);
        return chain;
    }

    /**
     * Mapper helper
     *
     * @param event
     */
    private Map<Class<? extends EventListener>, Handler> convertMapper(final Event event, HandlerMapping.HandlerMapperKey mapperKey) {
        lock.lock();
        try {
            if (DomainEvent.class.isAssignableFrom(event.getClass())) {
                Info.log(this.getClass(), "event is DomainEvent");
                List<EventListener> smartDomainEventListeners = getSmartDomainEventListener((DomainEvent) event);
                if (!smartDomainEventListeners.isEmpty()) {
                    return setDomainEventListenerMapper(mapperKey, smartDomainEventListeners);
                } else {
                    /*Collection<EventListener> listeners = Collections2.filter(this.configuration.eventListeners(), new Predicate<EventListener>() {
                        @Override
                        public boolean apply(EventListener eventListener) {
                            boolean include = CollectionUtils.arrayToList(eventListener.getClass().getInterfaces())
                                    .contains(DomainEventListener.class);
                            if (!include)
                                return false;
                            Type[] parameterizedType = ((ParameterizedType) eventListener.getClass().getGenericInterfaces()[0])
                                    .getActualTypeArguments();
                            Class<? extends DomainEvent> type = (Class<? extends DomainEvent>) parameterizedType[0].getClass();
                            return event.getClass().equals(type);
                        }
                    });*/
                    Collection<EventListener> listeners = getNotSmartListener(event, DomainEventListener.class);
                    return setDomainEventListenerMapper(mapperKey, Lists.newArrayList(listeners));
                }
            } else throw new ErrorEventTypeException();
        } finally {
            lock.unlock();
        }
    }

    private Collection<EventListener> getNotSmartListener(final Event event, final Class<? extends EventListener> Listeners$class) {
        return Collections2.filter(this.configuration.eventListeners(), new Predicate<EventListener>() {
                            @Override
                            public boolean apply(EventListener eventListener) {
                                boolean include = CollectionUtils.arrayToList(eventListener.getClass().getInterfaces())
                                        .contains(Listeners$class);
                                if (!include)
                                    return false;
                                Type[] parameterizedType = ((ParameterizedType) eventListener.getClass().getGenericInterfaces()[0])
                                        .getActualTypeArguments();
                                return event.getClass().equals(parameterizedType[0]);
                            }
                        });
    }

    private Map<Class<? extends EventListener>, Handler> setAppEventListenerMapper(HandlerMapping.HandlerMapperKey mapperKey, List<EventListener> listeners) {
        Map<Class<? extends EventListener>, Handler> machines = Maps.newHashMap();
        for (EventListener listener : listeners) {
            Handler handler = engine.assignApplicationEventHandler();
            handler.setEventListener(listener);
            machines.put(listener.getClass(), handler);
        }
        engine.getMapping().put(mapperKey, machines);
        return machines;
    }

    private List<EventListener> getSmartApplicationEventListener(final ApplicationEvent event) {
        List<EventListener> list = this.configuration.eventListeners();

        Collection<EventListener> result = filter(list, new Predicate<EventListener>() {
            @Override
            public boolean apply(EventListener eventListener) {
                if (!SmartApplicationListener.class.isAssignableFrom(eventListener.getClass()))
                    return false;
                else {
                    SmartApplicationListener listener = (SmartApplicationListener) eventListener;
                    boolean supportsEventType = listener.supportsEventType(event.getClass());
                    boolean supportsSourceType = listener.supportsSourceType(event.getSource().getClass());
                    if (supportsEventType && supportsSourceType)
                        return true;
                    return false;
                }
            }
        });

        List<EventListener> sorted = sorted(result);

        return sorted;
    }

    private List<EventListener> sorted(Collection<EventListener> result) {
        List<EventListener> listeners = Lists.newArrayList(result);
        Collections.sort(listeners, new Comparator<EventListener>() {
            @Override
            public int compare(EventListener o1, EventListener o2) {
                return ((SmartDomainEventListener) o1).getOrder() - ((SmartDomainEventListener) o2).getOrder();
            }
        });
        return listeners;
    }

    private Map<Class<? extends EventListener>, Handler> setDomainEventListenerMapper(HandlerMapping.HandlerMapperKey mapperKey,
                                                                                      List<EventListener> listeners) {
        Map<Class<? extends EventListener>, Handler> machines = Maps.newHashMap();
        for (EventListener listener : listeners) {
            Handler handler = engine.assignDomainEventHandler();
            handler.setEventListener(listener);
            machines.put(listener.getClass(), handler);
        }

        engine.getMapping().put(mapperKey, machines);
        return machines;
    }

    /**
     * 根据domainevent过滤出相应的listener
     * @param event
     * @return
     */
    private List<EventListener> getSmartDomainEventListener(final DomainEvent event) {
        List<EventListener> list = this.configuration.eventListeners();

        Collection<EventListener> result = filter(list, new Predicate<EventListener>() {
            @Override
            public boolean apply(EventListener eventListener) {
                if (!SmartDomainEventListener.class.isAssignableFrom(eventListener.getClass()))
                    return false;
                else {
                    SmartDomainEventListener listener = (SmartDomainEventListener) eventListener;
                    boolean supportsEventType = listener.supportsEventType(event.getClass());
                    boolean supportsSourceType = listener.supportsSourceType(event.getSource().getClass());
                    if (supportsEventType && supportsSourceType)
                        return true;
                    return false;
                }
            }
        });

        List<EventListener> sorted = sorted(result);
        return sorted;
    }

    private List<Interceptor> findInterceptor(final Event event) {
        if (this.configuration == null ||
                CollectionUtils.isEmpty(this.configuration.interceptors()))
            return Collections.emptyList();
        InterceptorCacheKey key = new InterceptorCacheKey(event.getClass());
        List<Interceptor> $interceptors = interceptorCache.get(key);
        if (CollectionUtils.isEmpty($interceptors)) {
            $interceptors = (List<Interceptor>) filter(this.configuration.interceptors(), new Predicate<Interceptor>() {
                @Override
                public boolean apply(Interceptor interceptor) {
                    return interceptor.supportsType(event.getClass());
                }
            });
            interceptorCache.put(key, $interceptors);
        }
        return $interceptors;
    }

    /**
     * getter and setter
     */
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    protected Configuration configuration() {
        return configuration;
    }

    protected PluginChain pluginChain() {
        return pluginChain;
    }

    protected EventContext context() {
        return context;
    }

    protected Engine engine() {
        return engine;
    }

    protected EngineDirector getEngineDirector() {
        return engineDirector;
    }

    protected EnginePolicy getEnginePolicy() {
        return enginePolicy;
    }

    static class InterceptorCacheKey {
        private Class<? extends Event> eventClz;

        public InterceptorCacheKey(Class<? extends Event> eventClz) {
            this.eventClz = eventClz;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            InterceptorCacheKey that = (InterceptorCacheKey) o;

            return ObjectUtils.nullSafeEquals(this.eventClz, that.eventClz);
        }

        @Override
        public int hashCode() {
            return ObjectUtils.nullSafeHashCode(this.eventClz) * 29;
        }
    }
}
