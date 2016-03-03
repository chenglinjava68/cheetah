package cheetah.core;

import cheetah.client.ProcessType;
import cheetah.common.Startable;
import cheetah.common.logger.Debug;
import cheetah.engine.Engine;
import cheetah.engine.EngineDirector;
import cheetah.engine.support.EnginePolicy;
import cheetah.event.*;
import cheetah.handler.Handler;
import cheetah.mapper.Mapper;
import cheetah.plugin.Interceptor;
import cheetah.plugin.InterceptorChain;
import cheetah.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * Created by Max on 2016/2/23.
 */
public abstract class AbstractDispatcher implements Dispatcher, Startable {

    private Configuration configuration; //框架配置
    private final InterceptorChain interceptorChain = new InterceptorChain(); //拦截器链
    private final ThreadLocal<Engine> currentEngine = new ThreadLocal<>();
    private Map<String, Engine> engineMap = new ConcurrentHashMap<>();
    private final ReentrantLock lock = new ReentrantLock();
    private final EventContext context = EventContext.getContext();

    /**
     * 接收事件消息，安排相应的engine对其进行处理
     * @param eventMessage
     * @param processType
     * @return
     */
    @Override
    public EventResult receive(final EventMessage eventMessage, final ProcessType processType) {
        try {
            Engine engine = engineSelect(processType, eventMessage);
            Event event = eventMessage.event();
            Mapper.HandlerMapperKey key = Mapper.HandlerMapperKey.generate(event.getClass(), event.getSource().getClass());

            boolean exists = engine.getMapper().isExists(key);
            if (exists) {
                Map<Class<? extends EventListener>, Handler> handlerMap = engine.getMapper().getMachine(key);
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

    /**
     * 根据策略选择一个引擎
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
     * @param policy
     * @return
     */
    private Engine createEngine(String policy) {
        EnginePolicy enginePolicy = EnginePolicy.formatFrom(policy);
        EngineDirector engineDirector = enginePolicy.getEngineDirector();
        engineDirector.setConfiguration(this.configuration);
        Engine engine = engineDirector.directEngine();
        this.engineMap.put(policy, engine);
        engine.setContext(context);
        engine.start();
        return engine;
    }

    @Override
    public void start() {
        if (Objects.nonNull(configuration)) {
            if (configuration.hasPlugin())
                initializesInterceptor(interceptorChain);
        } else
            configuration = new Configuration();
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

    public InterceptorChain initializesInterceptor(InterceptorChain chain) {
        Objects.requireNonNull(chain, "chain must not be null");
        for (Interceptor plugin : configuration.getPlugins())
            chain.addInterceptor(plugin);
        return chain;
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

    protected InterceptorChain getInterceptorChain() {
        return interceptorChain;
    }

    protected Engine getEngine() {
        return currentEngine.get();
    }

    public EventContext context() {
        return context;
    }

    public Engine getCurrentengine() {
        return currentEngine.get();
    }

    /**
     * Mapper helper
     *
     * @param event
     */
    private Map<Class<? extends EventListener>, Handler> convertMapper(Event event, Mapper.HandlerMapperKey mapperKey) {
        lock.lock();
        try {
            if (DomainEvent.class.isAssignableFrom(event.getClass())) {
                Debug.log(this.getClass(), "event is DomainEvent");
                List<EventListener> smartDomainEventListeners = getSmartDomainEventListener(event);
                if (!smartDomainEventListeners.isEmpty()) {
                    return setDomainEventListenerMapper(mapperKey, smartDomainEventListeners);
                } else {
                    List<EventListener> listeners = this.configuration.getEventListeners().
                            stream().filter(eventListener ->
                            CollectionUtils.arrayToList(eventListener.getClass().getInterfaces())
                                    .contains(DomainEventListener.class)).collect(Collectors.toList());
                    return setDomainEventListenerMapper(mapperKey, listeners);
                }
            } else if (ApplicationEvent.class.isAssignableFrom(event.getClass())) {
                Debug.log(this.getClass(), "event is ApplicationEvent");
                List<EventListener> smartAppEventListeners = getSmartApplicationEventListener(event);
                if (!smartAppEventListeners.isEmpty()) {
                    return setAppEventListenerMapper(mapperKey, smartAppEventListeners);
                } else {
                    List<EventListener> listeners = this.configuration.getEventListeners().
                            stream().filter(eventListener ->
                            CollectionUtils.arrayToList(eventListener.getClass().getInterfaces())
                                    .contains(ApplicationListener.class)).collect(Collectors.toList());
                    return setAppEventListenerMapper(mapperKey, listeners);
                }
            } else throw new ErrorEventTypeException();
        } finally {
            lock.unlock();
        }
    }

    private Map<Class<? extends EventListener>, Handler> setAppEventListenerMapper(Mapper.HandlerMapperKey mapperKey, List<EventListener> listeners) {
        Map<Class<? extends EventListener>, Handler> machines = listeners.stream().
                map(o -> {
                    Handler machine = currentEngine.get().assignApplicationEventHandler();
                    machine.setEventListener(o);
                    return machine;
                })
                .collect(Collectors.toMap(o -> o.getEventListener().getClass(), o -> o));
        this.currentEngine.get().getMapper().put(mapperKey, machines);
        return machines;
    }

    private List<EventListener> getSmartApplicationEventListener(Event event) {
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

    private Map<Class<? extends EventListener>, Handler> setDomainEventListenerMapper(Mapper.HandlerMapperKey mapperKey,
                                                                                      List<EventListener> listeners) {
        Map<Class<? extends EventListener>, Handler> machines = listeners.stream().
                map(o -> {
                    Handler machine = currentEngine.get().assignDomainEventHandler();
                    machine.setEventListener(o);
                    return machine;
                })
                .collect(Collectors.toMap(o -> o.getEventListener().getClass(), o -> o));

        this.currentEngine.get().getMapper().put(mapperKey, machines);
        return machines;
    }

    private List<EventListener> getSmartDomainEventListener(Event event) {
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
}
