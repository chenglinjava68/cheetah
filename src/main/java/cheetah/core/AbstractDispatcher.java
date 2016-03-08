package cheetah.core;

import cheetah.common.Startable;
import cheetah.common.logger.Debug;
import cheetah.common.utils.CollectionUtils;
import cheetah.common.utils.ObjectUtils;
import cheetah.common.utils.StringUtils;
import cheetah.event.*;
import cheetah.core.plugin.Plugin;
import cheetah.core.plugin.PluginChain;
import cheetah.engine.Engine;
import cheetah.engine.EngineDirector;
import cheetah.engine.support.EnginePolicy;
import cheetah.handler.Handler;
import cheetah.mapping.HandlerMapping;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.EventListener;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

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
        try {
            getContext().setEventMessage(eventMessage);
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
        }
    }


    @Override
    public void start() {
        if (Objects.nonNull(configuration)) {
            if (configuration.hasPlugin())
                initializesPlugin(pluginChain);
        } else
            configuration = new Configuration();
        if (StringUtils.isEmpty(configuration.getPolicy())) {
            enginePolicy = EnginePolicy.DISRUPTOR;
            engineDirector = enginePolicy.getEngineDirector();
        } else {
            enginePolicy = EnginePolicy.formatFrom(configuration.getPolicy());
            engineDirector = enginePolicy.getEngineDirector();
        }
        engineDirector.setConfiguration(this.configuration);
        engine = engineDirector.directEngine();
        engine.setContext(this.getContext());
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
        for (Plugin plugin : configuration.getPlugins())
            chain.register(plugin);
        return chain;
    }

    /**
     * Mapper helper
     *
     * @param event
     */
    private Map<Class<? extends EventListener>, Handler> convertMapper(Event event, HandlerMapping.HandlerMapperKey mapperKey) {
        lock.lock();
        try {
            if (DomainEvent.class.isAssignableFrom(event.getClass())) {
                Debug.log(this.getClass(), "event is DomainEvent");
                List<EventListener> smartDomainEventListeners = getSmartDomainEventListener((DomainEvent) event);
                if (!smartDomainEventListeners.isEmpty()) {
                    return setDomainEventListenerMapper(mapperKey, smartDomainEventListeners);
                } else {
                    List<EventListener> listeners = this.configuration.getEventListeners().
                            stream().filter(eventListener ->
                            CollectionUtils.arrayToList(eventListener.getClass().getInterfaces())
                                    .contains(DomainEventListener.class))
                            .filter(o -> {
                                Type[] parameterizedType = ((ParameterizedType) o.getClass().getGenericInterfaces()[0])
                                        .getActualTypeArguments();
                                Class<? extends DomainEvent> type = (Class<? extends DomainEvent>) parameterizedType[0];
                                return event.getClass().equals(type);
                            }).collect(Collectors.toList());
                    return setDomainEventListenerMapper(mapperKey, listeners);
                }
            } else if (ApplicationEvent.class.isAssignableFrom(event.getClass())) {
                Debug.log(this.getClass(), "event is ApplicationEvent");
                List<EventListener> smartAppEventListeners = getSmartApplicationEventListener((ApplicationEvent) event);
                if (!smartAppEventListeners.isEmpty()) {
                    return setAppEventListenerMapper(mapperKey, smartAppEventListeners);
                } else {
                    List<EventListener> listeners = this.configuration.getEventListeners().
                            stream().filter(eventListener ->
                            CollectionUtils.arrayToList(eventListener.getClass().getInterfaces())
                                    .contains(ApplicationListener.class))
                            .filter(o -> {
                                Type[] parameterizedType = ((ParameterizedType) o.getClass().getGenericInterfaces()[0])
                                        .getActualTypeArguments();
                                Class<? extends ApplicationEvent> type = (Class<? extends ApplicationEvent>) parameterizedType[0];
                                return event.getClass().equals(type);
                            }).collect(Collectors.toList());
                    return setAppEventListenerMapper(mapperKey, listeners);
                }
            } else throw new ErrorEventTypeException();
        } finally {
            lock.unlock();
        }
    }

    private Map<Class<? extends EventListener>, Handler> setAppEventListenerMapper(HandlerMapping.HandlerMapperKey mapperKey, List<EventListener> listeners) {
        Map<Class<? extends EventListener>, Handler> machines = listeners.stream().
                map(o -> {
                    Handler machine = engine.assignApplicationEventHandler();
                    machine.setEventListener(o);
                    return machine;
                })
                .collect(Collectors.toMap(o -> o.getEventListener().getClass(), o -> o));
        engine.getMapping().put(mapperKey, machines);
        return machines;
    }

    private List<EventListener> getSmartApplicationEventListener(ApplicationEvent event) {
        return this.configuration.getEventListeners().
                stream().filter(eventListener ->
                SmartApplicationListener.class.isAssignableFrom(eventListener.getClass()))
                .filter(eventListener -> {
                    SmartApplicationListener listener = (SmartApplicationListener) eventListener;
                    boolean supportsEventType = listener.supportsEventType(event.getClass());
                    boolean supportsSourceType = listener.supportsSourceType(event.getSource().getClass());
                    return supportsEventType && supportsSourceType;
                }).map(listener -> (SmartApplicationListener) listener).sorted((o1, o2) ->
                                o1.getOrder() - o2.getOrder()
                ).collect(Collectors.toList());
    }

    private Map<Class<? extends EventListener>, Handler> setDomainEventListenerMapper(HandlerMapping.HandlerMapperKey mapperKey,
                                                                                      List<EventListener> listeners) {
        Map<Class<? extends EventListener>, Handler> machines = listeners.stream().
                map(o -> {
                    Handler machine = engine.assignDomainEventHandler();
                    machine.setEventListener(o);
                    return machine;
                })
                .collect(Collectors.toMap(o -> o.getEventListener().getClass(), o -> o));

        engine.getMapping().put(mapperKey, machines);
        return machines;
    }

    private List<EventListener> getSmartDomainEventListener(DomainEvent event) {
        return this.configuration.getEventListeners().
                stream().filter(eventListener ->
                SmartDomainEventListener.class.isAssignableFrom(eventListener.getClass()))
                .filter(eventListener -> {
                    SmartDomainEventListener listener = (SmartDomainEventListener) eventListener;
                    boolean supportsEventType = listener.supportsEventType(event.getClass());
                    boolean supportsSourceType = listener.supportsSourceType(event.getSource().getClass());
                    return supportsEventType && supportsSourceType;
                }).map(listener -> (SmartDomainEventListener) listener).sorted((o1, o2) ->
                                o1.getOrder() - o2.getOrder()
                ).collect(Collectors.toList());
    }


    private List<Interceptor> findInterceptor(Event event) {
        InterceptorCacheKey key = new InterceptorCacheKey(event.getClass());
        List<Interceptor> $interceptors = interceptorCache.get(key);
        if (CollectionUtils.isEmpty($interceptors)) {
            $interceptors = this.configuration.getInterceptors().stream().filter(i ->
                            i.supportsType(event.getClass())
            ).collect(Collectors.toList());
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

    protected Configuration getConfiguration() {
        return configuration;
    }

    protected PluginChain getPluginChain() {
        return pluginChain;
    }

    protected EventContext getContext() {
        return context;
    }

    protected Engine getEngine() {
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
