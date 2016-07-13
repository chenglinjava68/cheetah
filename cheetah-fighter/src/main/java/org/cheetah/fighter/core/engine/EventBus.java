package org.cheetah.fighter.core.engine;

import com.google.common.collect.Maps;
import org.cheetah.commons.Startable;
import org.cheetah.commons.logger.Info;
import org.cheetah.commons.logger.Loggers;
import org.cheetah.commons.utils.CollectionUtils;
import org.cheetah.commons.utils.ObjectUtils;
import org.cheetah.commons.utils.StringUtils;
import org.cheetah.fighter.core.*;
import org.cheetah.fighter.core.event.DomainEvent;
import org.cheetah.fighter.core.event.DomainEventListener;
import org.cheetah.fighter.core.event.Event;
import org.cheetah.fighter.core.event.SmartDomainEventListener;
import org.cheetah.fighter.core.governor.Governor;
import org.cheetah.fighter.core.handler.Feedback;
import org.cheetah.fighter.core.handler.Handler;
import org.cheetah.fighter.engine.EnginePolicy;
import org.cheetah.fighter.plugin.Plugin;
import org.cheetah.fighter.plugin.PluginChain;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * Created by Max on 2016/1/29.
 */
public class EventBus implements Dispatcher, Startable {

    private FighterConfig fighterConfig; //框架配置
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
            Map<Class<? extends EventListener>, Handler> handlerMap = eventResolve(event, key);
            context.setHandlers(handlerMap);
            return dispatch();
        } finally {
            context.removeEventMessage();
            context.removeHandlers();
            context.removeInterceptor();
        }
    }

    @Override
    public EventResult dispatch() {
        EventMessage eventMessage = context().eventMessage();
        Map<Class<? extends EventListener>, Handler> handlerMap = context().handlers();
        if (!handlerMap.isEmpty()) {
            try {
                Governor governor = engine().assignGovernor();
                Feedback report = governor.initialize()
                        .accept(eventMessage)
                        .registerHandlerSquad(handlerMap)
                        .command();
                return new EventResult(eventMessage.event().getSource(), report.isFail());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        throw new NoMapperException();
    }

    @Override
    public void start() {
        if (fighterConfig != null) {
            if (fighterConfig.hasPlugin())
                initializesPlugin(pluginChain);
        } else
            fighterConfig = new FighterConfig();
        if (StringUtils.isEmpty(fighterConfig.policy())) {
            enginePolicy = EnginePolicy.DISRUPTOR;
            engineDirector = enginePolicy.getEngineDirector();
        } else {
            enginePolicy = EnginePolicy.formatFrom(fighterConfig.policy());
            engineDirector = enginePolicy.getEngineDirector();
        }
        engineDirector.setFighterConfig(this.fighterConfig);
        engine = engineDirector.directEngine();
        engine.setContext(this.context());
        engine.registerPluginChain(pluginChain);
        engine.start();
        Info.log(this.getClass(), "EventBus start engine is {}", enginePolicy.name());
    }

    @Override
    public void stop() {
        this.engine.stop();
        this.engine = null;
        engineDirector = null;
        enginePolicy = null;
        Info.log(this.getClass(), "EventBus stop...");
    }

    public PluginChain initializesPlugin(PluginChain chain) {
        Objects.requireNonNull(chain, "chain must not be null");
        for (Plugin plugin : fighterConfig.plugins())
            chain.register(plugin);
        return chain;
    }

    /**
     * Mapper helper
     *
     * @param event
     */
    private Map<Class<? extends EventListener>, Handler> eventResolve(final Event event, HandlerMapping.HandlerMapperKey mapperKey) {
        lock.lock();
        try {
            if (DomainEvent.class.isAssignableFrom(event.getClass())) {
                List<EventListener> eventListeners = supportsSmartListener((DomainEvent) event);
                if (eventListeners.isEmpty()) {
                     eventListeners = supportsUniversalListener(event);
                }
                return assembleEventHandlerMapping(mapperKey, eventListeners);
            } else
                Loggers.me().error(this.getClass(), "The incident no listener corresponding processing.");
        } finally {
            lock.unlock();
        }
        return Maps.newHashMap();
    }

    @SuppressWarnings("unchecked")
    private List<EventListener> supportsUniversalListener(final Event event) {
        List<EventListener> eventListeners = this.fighterConfig.eventListeners();

        return eventListeners.stream().filter(o ->{
            List classes = CollectionUtils.arrayToList(o.getClass().getInterfaces());
            Optional<Class> classOptional = classes.stream().filter(it -> it.equals(DomainEventListener.class)).findFirst();
            if(!classOptional.isPresent())
                return false;
            Type[] parameterizedType = ((ParameterizedType) o.getClass().getGenericInterfaces()[0])
                    .getActualTypeArguments();
            if(parameterizedType.length < 1)
                return true;
            return event.getClass().equals(parameterizedType[0]);
        }).collect(Collectors.toList());
    }

    private Map<Class<? extends EventListener>, Handler> assembleEventHandlerMapping(HandlerMapping.HandlerMapperKey mapperKey,
                                                                                     List<EventListener> listeners) {
        Map<Class<? extends EventListener>, Handler> handlers = Maps.newHashMap();
        for (EventListener listener : listeners) {
            Handler handler = engine.assignDomainEventHandler();
            handler.setEventListener(listener);
            handlers.put(listener.getClass(), handler);
        }

        engine.getMapping().put(mapperKey, handlers);
        return handlers;
    }

    /**
     * 根据domainevent过滤出相应的listener
     * @param event
     * @return
     */
    private List<EventListener> supportsSmartListener(final DomainEvent event) {
        List<EventListener> list = this.fighterConfig.eventListeners();

        return list.stream().filter(o -> SmartDomainEventListener.class.isAssignableFrom(o.getClass()))
                .map(o -> ((SmartDomainEventListener) o))
                .filter(o -> o.supportsEventType(event.getClass()))
                .filter(o -> o.supportsSourceType(event.getSource().getClass()))
                .collect(Collectors.toList());
    }

    private List<Interceptor> findInterceptor(final Event event) {
        List<Interceptor> interceptors = this.fighterConfig.interceptors();
        if (this.fighterConfig == null ||
                CollectionUtils.isEmpty(interceptors))
            return Collections.emptyList();
        InterceptorCacheKey key = new InterceptorCacheKey(event.getClass());
        List<Interceptor> $interceptors = interceptorCache.get(key);
        if (CollectionUtils.isEmpty($interceptors)) {
            $interceptors = interceptors.stream().filter(o -> o.supportsType(event.getClass())).collect(Collectors.toList());
            interceptorCache.put(key, $interceptors);
        }
        return $interceptors;
    }

    /**
     * getter and setter
     */
    public void setFighterConfig(FighterConfig fighterConfig) {
        this.fighterConfig = fighterConfig;
    }

    protected FighterConfig fighterConfig() {
        return fighterConfig;
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
