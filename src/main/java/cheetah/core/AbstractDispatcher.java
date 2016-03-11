package cheetah.core;

import cheetah.api.ProcessType;
import cheetah.common.Startable;
import cheetah.common.logger.Debug;
import cheetah.common.utils.CollectionUtils;
import cheetah.common.utils.ObjectUtils;
import cheetah.core.plugin.Plugin;
import cheetah.core.plugin.PluginChain;
import cheetah.engine.Engine;
import cheetah.engine.EngineDirector;
import cheetah.engine.support.EnginePolicy;
import cheetah.event.*;
import cheetah.handler.Handler;
import cheetah.mapping.HandlerMapping;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * Created by Max on 2016/2/23.
 */
public abstract class AbstractDispatcher implements Dispatcher, Startable {

    private Configuration configuration; //框架配置
    private final PluginChain pluginChain = new PluginChain();
    private final ThreadLocal<Engine> currentEngine = new ThreadLocal<>();
    private Map<String, Engine> engineMap = new ConcurrentHashMap<>();
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
    public EventResult receive(final EventMessage eventMessage, ProcessType processType) {
        try {
            Engine engine = engineSelect(processType, eventMessage);
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
            currentEngine.remove();
        }
    }

    /**
     * 根据策略选择一个引擎
     *
     * @param processType
     * @param eventMessage
     * @return
     */
    private Engine engineSelect(ProcessType processType, EventMessage eventMessage) {
        Engine engine = buildEngine(processType.policy());
        context.setEventMessage(eventMessage);
        currentEngine.set(engine);
        return engine;
    }

    /**
     * 通过policy构建一个引擎，如果map中有，就从map中取，不重复重建
     *
     * @param policy
     * @return
     */
    private Engine buildEngine(String policy) {
        Engine engine = engineMap.get(policy);
        if (Objects.nonNull(engine)) {
            return engine;
        }
        synchronized (this) {
            return createEngine(policy);
        }
    }

    /**
     * 根据policy创建一个引擎
     *
     * @param policy
     * @return
     */
    private Engine createEngine(String policy) {
        EnginePolicy enginePolicy = EnginePolicy.formatFrom(policy);
        EngineDirector engineDirector = enginePolicy.getEngineDirector();
        engineDirector.setConfiguration(this.configuration);
        Engine engine = engineDirector.directEngine();
        engine.registerPluginChain(pluginChain);
        this.engineMap.put(policy, engine);
        engine.setContext(context);
        engine.start();
        return engine;
    }


    @Override
    public void start() {
        if (Objects.nonNull(configuration)) {
            if (configuration.hasPlugin())
                initializesPlugin(pluginChain);
        } else
            configuration = new Configuration();
//        if (StringUtils.isEmpty(configuration.policy())) {
//            enginePolicy = EnginePolicy.DISRUPTOR;
//            engineDirector = enginePolicy.getEngineDirector();
//        } else {
//            enginePolicy = EnginePolicy.formatFrom(configuration.policy());
//            engineDirector = enginePolicy.getEngineDirector();
//        }
//        engineDirector.setConfiguration(this.configuration);
//        engine = engineDirector.directEngine();
//        engine.setContext(this.context());
//        engine.registerPluginChain(pluginChain);
//        engine.start();
    }

    @Override
    public void stop() {
        Map<String, Engine> tempEngine = new HashMap<>(engineMap);
        engineMap.clear();
        Iterator<Engine> engineIterator = tempEngine.values().iterator();
        while (engineIterator.hasNext()) {
            engineIterator.next().stop();
        }
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
    private Map<Class<? extends EventListener>, Handler> convertMapper(Event event, HandlerMapping.HandlerMapperKey mapperKey) {
        lock.lock();
        try {
            if (DomainEvent.class.isAssignableFrom(event.getClass())) {
                Debug.log(this.getClass(), "event is DomainEvent");
                List<EventListener> smartDomainEventListeners = getSmartDomainEventListener((DomainEvent) event);
                if (!smartDomainEventListeners.isEmpty()) {
                    return setDomainEventListenerMapper(mapperKey, smartDomainEventListeners);
                } else {
                    List<EventListener> listeners = this.configuration.eventListeners().
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
                    List<EventListener> listeners = this.configuration.eventListeners().
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
                    Handler machine = currentEngine.get().assignApplicationEventHandler();
                    machine.setEventListener(o);
                    return machine;
                })
                .collect(Collectors.toMap(o -> o.getEventListener().getClass(), o -> o));
        currentEngine.get().getMapping().put(mapperKey, machines);
        return machines;
    }

    private List<EventListener> getSmartApplicationEventListener(ApplicationEvent event) {
        return this.configuration.eventListeners().
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
                    Handler machine = currentEngine.get().assignDomainEventHandler();
                    machine.setEventListener(o);
                    return machine;
                })
                .collect(Collectors.toMap(o -> o.getEventListener().getClass(), o -> o));

        currentEngine.get().getMapping().put(mapperKey, machines);
        return machines;
    }

    private List<EventListener> getSmartDomainEventListener(DomainEvent event) {
        return this.configuration.eventListeners().
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
        if (Objects.isNull(this.configuration) ||
                CollectionUtils.isEmpty(this.configuration.interceptors()))
            return Collections.emptyList();
        InterceptorCacheKey key = new InterceptorCacheKey(event.getClass());
        List<Interceptor> $interceptors = interceptorCache.get(key);
        if (CollectionUtils.isEmpty($interceptors)) {
            $interceptors = this.configuration.interceptors().stream().filter(i ->
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
        return currentEngine.get();
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
