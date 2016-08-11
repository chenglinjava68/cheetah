package org.cheetah.fighter;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.cheetah.commons.Startable;
import org.cheetah.commons.logger.Info;
import org.cheetah.commons.logger.Warn;
import org.cheetah.commons.utils.CollectionUtils;
import org.cheetah.commons.utils.ObjectUtils;
import org.cheetah.commons.utils.StringUtils;
import org.cheetah.fighter.engine.Engine;
import org.cheetah.fighter.engine.EngineDirector;
import org.cheetah.fighter.engine.support.EngineStrategy;
import org.cheetah.fighter.handler.Handler;
import org.cheetah.fighter.plugin.Plugin;
import org.cheetah.fighter.plugin.PluginChain;
import org.cheetah.fighter.worker.WorkerAdapter;
import org.cheetah.ioc.BeanFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * Event调度器
 * Created by Max on 2016/1/29.
 */
public class EventBus implements Dispatcher, Startable {
    /**
     * 框架配置
     */
    private FighterConfig fighterConfig;
    /**
     * 注册的插件列表
     */
    private List<Plugin> plugins = ImmutableList.of();
    /**
     * 注册的拦截器列表
     */
    private List<Interceptor> interceptors = ImmutableList.of();
    /**
     * 注册的消费者列表
     */
    private List<DomainEventListener> eventListeners = ImmutableList.of();
    /**
     * 插件链
     */
    private final PluginChain pluginChain = new PluginChain();
    /**
     * 使用的引擎
     */
    private Engine engine;
    /**
     * 引擎的管理者，为bus构建引擎
     */
    private EngineDirector engineDirector;
    /**
     * 引擎策略
     */
    private EngineStrategy engineStrategy;
    /**
     * 事件拦截器缓存
     */
    private final Map<InterceptorCacheKey, List<Interceptor>> interceptorCache;
    /**
     * 事件处理器的缓存
     */
    private Map<HandlerMapperKey, List<Handler>> eventHandlers;
    /**
     * 为事件查找handler时使用的锁
     */
    private final ReentrantLock lock;
    /**
     * 事件上下文
     */
    private final EventContext context;

    public EventBus() {
        this.interceptorCache = new ConcurrentHashMap<>();
        this.eventHandlers = new ConcurrentHashMap<>();
        this.lock = new ReentrantLock();
        this.context = EventContext.getContext();
    }

    /**
     * 每个事件的调度由此开始
     * @param eventMessage
     * @return
     */
    @Override
    public EventResult dispatch(final EventMessage eventMessage) {
        try {
            context().setEventMessage(eventMessage);
            DomainEvent event = eventMessage.event();
            HandlerMapperKey key = HandlerMapperKey.generate(event.getClass(), event.getSource().getClass());
            List<Interceptor> interceptors = findInterceptor(event);
            context.setInterceptor(interceptors);
            boolean exists = eventHandlers.containsKey(key);
            if (exists) {
                List<Handler> handlerMap = eventHandlers.get(key);
                context.setHandlers(handlerMap);
                return doDispatch(eventMessage);
            }
            List<Handler> handlers = getHandlers(event, key);
            if (handlers.isEmpty()) {
                Warn.log(this.getClass(), "Couldn't find the corresponding mapping.");
                throw new NoMapperException();
            }
            context.setHandlers(handlers);
            return doDispatch(eventMessage);
        } finally {
            context.removeEventMessage();
            context.removeHandlers();
            context.removeInterceptor();
        }
    }

    /**
     * 找到相应的orkerAdapter进行调度
     * @param eventMessage
     * @return
     */
    @SuppressWarnings("unchecked")
    private EventResult doDispatch(EventMessage eventMessage) {
        WorkerAdapter workerAdapter = this.engine.assignWorkerAdapter();
        Feedback feedback = workerAdapter.work(eventMessage);
        return new EventResult(eventMessage.event().source, feedback.isSuccess(), feedback.getExceptionMap());
    }

    /**
     * 启动，进行初始化数据
     */
    @Override
    public void start() {
        if(fighterConfig == null)
            this.fighterConfig = FighterConfig.getDefaultConfig();
        if (!plugins.isEmpty())
            initializesPlugin(pluginChain);

        if (StringUtils.isEmpty(fighterConfig.getEngine())) {
            engineStrategy = EngineStrategy.FUTURE;
            engineDirector = engineStrategy.getEngineDirector();
        } else {
            engineStrategy = EngineStrategy.getEngine(fighterConfig.getEngine());
            engineDirector = engineStrategy.getEngineDirector();
        }
        engineDirector.setFighterConfig(this.fighterConfig);
        engine = engineDirector.directEngine();
        engine.setContext(this.context());
        engine.registerPluginChain(pluginChain);
        engine.start();
        Info.log(this.getClass(), "EventBus start engine is {}", engineStrategy.name());
    }

    /**
     * 停止EventBus
     */
    @Override
    public void stop() {
        this.engine.stop();
        this.engine = null;
        engineDirector = null;
        engineStrategy = null;
        Warn.log(this.getClass(), "EventBus stop...");
    }

    /**
     * 根据事件查找其对应的处理器
     * @param event
     * @param mapperKey
     * @return
     */
    private List<Handler> getHandlers(final DomainEvent event, final HandlerMapperKey mapperKey) {
        return resolve(event, mapperKey);
    }

    /**
     * 解析事件类型，
     *
     * @param event
     */
    private List<Handler> resolve(final DomainEvent event, final HandlerMapperKey mapperKey) {
        lock.lock();
        try {
            if (DomainEvent.class.isAssignableFrom(event.getClass())) {
                List<DomainEventListener> eventListeners = supportsSmartListener(event);
                if (eventListeners.isEmpty()) {
                    eventListeners = supportsUniversalListener(event);
                }

                if (Info.isEnabled(this.getClass())) {
                    eventListeners.forEach(o ->
                        Info.log(this.getClass(), "parse event listeners: {}", o.getClass().getName())
                    );
                }
                return assembleEventHandlerMapping(mapperKey, eventListeners);
            }
        } finally {
            lock.unlock();
        }
        return Collections.emptyList();
    }

    /**
     * 找出非Smart类型的消费者
     * @param event
     * @return
     */
    @SuppressWarnings("unchecked")
    private List<DomainEventListener> supportsUniversalListener(final Event event) {
        List<DomainEventListener> eventListeners = Lists.newArrayList(this.eventListeners);
        merge(eventListeners, DomainEventListener.class);

        return eventListeners.stream().filter(o -> {
            List classes = CollectionUtils.arrayToList(o.getClass().getInterfaces());
            Optional<Class> classOptional = classes.stream().filter(it -> it.equals(DomainEventListener.class)).findFirst();
            if (!classOptional.isPresent())
                return false;
            Type type = o.getClass().getGenericInterfaces()[0];
            if (!(type instanceof ParameterizedType))
                return true;
            Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
            if (parameterizedType.length < 1)
                return true;
            return event.getClass().equals(parameterizedType[0]);
        }).collect(Collectors.toList());
    }

    /**
     * 将消费者包装成Handler
     * @param mapperKey
     * @param listeners
     * @return
     */
    private List<Handler> assembleEventHandlerMapping(HandlerMapperKey mapperKey,
                                                      List<DomainEventListener> listeners) {
        List<Handler> handlers = listeners.stream()
                .map(engine::assignDomainEventHandler)
                .collect(Collectors.toList());

        eventHandlers.put(mapperKey, handlers);
        return handlers;
    }

    /**
     * 根据domainevent过滤出相应的listener
     * @param event
     * @return
     */
    private List<DomainEventListener> supportsSmartListener(final DomainEvent event) {
        List<DomainEventListener> list = Lists.newArrayList(this.eventListeners);

        merge(list, DomainEventListener.class);

        return list.stream().filter(o -> SmartDomainEventListener.class.isAssignableFrom(o.getClass()))
                .map(o -> ((SmartDomainEventListener) o))
                .filter(o -> o.supportsEventType(event.getClass()))
                .filter(o -> o.supportsSourceType(event.getSource().getClass()))
                .collect(Collectors.toList());
    }

    /**
     * 将本地的eventlistener或者interceptor与spring工厂中的合并
     * @param list
     */
    private <T> void merge(List<T> list, Class<T> objClz) {
        Map<String, T> listenerMap = BeanFactory.getBeansOfType(objClz);
        if (!CollectionUtils.isEmpty(listenerMap)) {
            Collection<T> collection = listenerMap.values();
            collection.forEach(o -> {
                if (list.contains(o))
                    return ;
                list.add(o);
            });
        }
    }

    /**
     * 初始化插件链
     * @param chain
     * @return
     */
    private PluginChain initializesPlugin(PluginChain chain) {
        Objects.requireNonNull(chain, "chain must not be null");
        for (Plugin plugin : this.plugins)
            chain.register(plugin);
        return chain;
    }

    /**
     * 查找事件对应的拦截器
     * @param event
     * @return
     */
    private List<Interceptor> findInterceptor(final DomainEvent event) {
        List<Interceptor> interceptors = Lists.newArrayList(this.interceptors);
        merge(interceptors, Interceptor.class);

        if (CollectionUtils.isEmpty(interceptors))
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
     * 注册一个插件
     * @param plugin
     */
    public void registerPlugin(Plugin plugin) {
        this.plugins = ImmutableList.<Plugin>builder().addAll(this.plugins).add(plugin).build();
    }

    /**
     * 注册一个拦截器
     * @param interceptor
     */
    public void registerInterceptor(Interceptor interceptor) {
        this.interceptors = ImmutableList.<Interceptor>builder().addAll(this.interceptors).add(interceptor).build();
    }

    /**
     * 注册一个监听器
     * @param eventListener
     */
    public void registerEventListener(DomainEventListener eventListener) {
        this.eventListeners = ImmutableList.<DomainEventListener>builder().addAll(this.eventListeners).add(eventListener).build();
    }

    /**
     * 注册一组插件
     * @param plugins
     */
    public void registerPlugins(List<Plugin> plugins) {
        this.plugins = ImmutableList.<Plugin>builder().addAll(this.plugins).addAll(plugins).build();
    }

    /**
     * 注册一组拦截器
     * @param interceptors
     */
    public void registerInterceptors(List<Interceptor> interceptors) {
        this.interceptors = ImmutableList.<Interceptor>builder().addAll(this.interceptors).addAll(interceptors).build();
    }

    /**
     * 注册一组监听器
     * @param eventListeners
     */
    public void registerEventListeners(List<DomainEventListener> eventListeners) {
        this.eventListeners = ImmutableList.<DomainEventListener>builder().addAll(this.eventListeners).addAll(eventListeners).build();
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

    protected EngineStrategy getEngineStrategy() {
        return engineStrategy;
    }

    public void setPlugins(List<Plugin> plugins) {
        this.plugins = ImmutableList.<Plugin>builder().addAll(this.plugins).addAll(plugins).build();
    }

    public void setInterceptors(List<Interceptor> interceptors) {
        this.interceptors = ImmutableList.<Interceptor>builder().addAll(this.interceptors).addAll(interceptors).build();
    }

    public void setEventListeners(List<DomainEventListener> eventListeners) {
        this.eventListeners = ImmutableList.<DomainEventListener>builder().addAll(this.eventListeners).addAll(eventListeners).build();
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

    public static class HandlerMapperKey {
        private final Class<?> eventType;
        private final Class<?> sourceType;

        public HandlerMapperKey(Class<?> eventType, Class<?> sourceType) {
            this.eventType = eventType;
            this.sourceType = sourceType;
        }

        public static HandlerMapperKey generate(Class<?> eventType, Class<?> sourceType) {
            return new HandlerMapperKey(eventType, sourceType);
        }

        public static HandlerMapperKey generate(Event event) {
            return new HandlerMapperKey(event.getClass(), event.getSource().getClass());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            HandlerMapperKey that = (HandlerMapperKey) o;

            return ObjectUtils.nullSafeEquals(this.eventType, that.eventType) && ObjectUtils.nullSafeEquals(this.sourceType, that.sourceType);
        }

        @Override
        public int hashCode() {
            return ObjectUtils.nullSafeHashCode(this.eventType) * 29 + ObjectUtils.nullSafeHashCode(this.sourceType);
        }
    }
}
