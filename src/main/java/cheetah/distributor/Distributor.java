package cheetah.distributor;

import cheetah.distributor.handler.Handlers;
import cheetah.event.*;
import cheetah.exceptions.ErrorEventTypeException;
import cheetah.plugin.Interceptor;
import cheetah.plugin.InterceptorChain;
import cheetah.util.Assert;
import cheetah.util.CollectionUtils;
import cheetah.util.ObjectUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Created by Max on 2016/1/29.
 */
public class Distributor implements Startable, Governor {
    public enum STATE {
        NEW, RUNNING, STOP
    }

    private ExecutorService executorService;
    private Configuration configuration;
    private final InterceptorChain interceptorChain = new InterceptorChain();
    private final Map<ListenerCacheKey, List<EventListener>> listenerCache = new ConcurrentHashMap<>();
    private Handlers handlers;
    private volatile STATE state;
    private final Map<Class<? extends Event>, Collector> collectors = new ConcurrentHashMap<>();

    public Distributor() {
        this.state = STATE.NEW;
        this.executorService = Executors.newCachedThreadPool();
    }

    @Override
    public void start() {
        if (Objects.nonNull(configuration)) {
            if (configuration.hasPlugin())
                initializesInterceptor(interceptorChain);
        } else
            configuration = new Configuration();

        if (Objects.isNull(executorService)) {
            this.executorService = Executors.newCachedThreadPool();
        }

        registrationHandlers();
        this.state = STATE.RUNNING;
    }

    @Override
    public void stop() {
        checkup();
        while (!executorService.isShutdown()) {
            try {
                executorService.shutdownNow();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        executorService = null;
        this.state = STATE.STOP;
    }

    public void checkup() {
        if (!this.state.equals(STATE.RUNNING))
            throw new DistributorStateException();
    }

    @Override
    public EventResult allot(EventMessage eventMessage) {
        checkup();
        Event event = eventMessage.getEvent();
        ListenerCacheKey cacheKey = ListenerCacheKey.generator(event.getClass(), event.getSource().getClass());
        List<EventListener> cacheListner = getSmartEventListenerFromCache(cacheKey);
        boolean noCache = cacheListner.isEmpty();
        if (noCache) {
            if (DomainEvent.class.isAssignableFrom(eventMessage.getEvent().getClass())) {
                List<EventListener> smartDomainEventListeners = this.configuration.getEventListeners().
                        stream().filter(eventListener ->
                        SmartDomainEventListener.class.isAssignableFrom(eventListener.getClass()))
                        .filter(eventListener -> {
                            SmartDomainEventListener listener = (SmartDomainEventListener) eventListener;
                            boolean supportsEventType = listener.supportsEventType(eventMessage.getEvent().getClass());
                            boolean supportsSourceType = listener.supportsSourceType(eventMessage.getEvent().getSource().getClass());
                            return supportsEventType && supportsSourceType;
                        }).map(listener -> (SmartDomainEventListener) listener).sorted((o1, o2) ->
                                        o1.getOrder() - o2.getOrder()
                        ).collect(Collectors.toList());
                if (!smartDomainEventListeners.isEmpty()) {
                    this.listenerCache.put(ListenerCacheKey.generator(event.getClass(), event.getSource().getClass()), smartDomainEventListeners);
                    return this.handlers.handle(eventMessage, smartDomainEventListeners);
                } else {
                    List<EventListener> listeners = this.configuration.getEventListeners().
                            stream().filter(eventListener ->
                            CollectionUtils.arrayToList(eventListener.getClass().getInterfaces())
                                    .contains(DomainEventListener.class)).collect(Collectors.toList());
                    this.listenerCache.put(ListenerCacheKey.generator(event.getClass(), event.getSource().getClass()), listeners);
                    return this.handlers.handle(eventMessage, listeners);
                }
            } else if (ApplicationEvent.class.isAssignableFrom(eventMessage.getEvent().getClass())) {
                List<EventListener> smartAppEventListeners = this.configuration.getEventListeners().
                        stream().filter(eventListener ->
                        SmartApplicationListener.class.isAssignableFrom(eventListener.getClass()))
                        .filter(eventListener -> {
                            SmartApplicationListener listener = (SmartApplicationListener) eventListener;
                            boolean supportsEventType = listener.supportsEventType(eventMessage.getEvent().getClass());
                            boolean supportsSourceType = listener.supportsSourceType(eventMessage.getEvent().getSource().getClass());
                            return supportsEventType && supportsSourceType;
                        }).map(listener -> (SmartApplicationListener) listener).sorted((o1, o2) ->
                                        o1.getOrder() - o2.getOrder()
                        ).collect(Collectors.toList());
                this.listenerCache.put(ListenerCacheKey.generator(event.getClass(), event.getSource().getClass()), smartAppEventListeners);
                if (!smartAppEventListeners.isEmpty())
                    return this.handlers.handle(eventMessage, smartAppEventListeners);
                else {
                    List<EventListener> listeners = this.configuration.getEventListeners().
                            stream().filter(eventListener ->
                            CollectionUtils.arrayToList(eventListener.getClass().getInterfaces())
                                    .contains(ApplicationListener.class)).collect(Collectors.toList());
                    this.listenerCache.put(ListenerCacheKey.generator(event.getClass(), event.getSource().getClass()), listeners);
                    return this.handlers.handle(eventMessage, listeners);
                }
            } else throw new ErrorEventTypeException();
        } else
            return this.handlers.handle(eventMessage, cacheListner);
    }

    @Override
    public void registrationCollector(Class<? extends Event> key, Collector collector) {
        Assert.notNull(key, "key must not be null");
        Assert.notNull(collector, "collector must not be null");
        collectors.put(key, collector);
    }

    @Override
    public Collector arrangeCollector(Class<? extends Event> collectorType) {
        Assert.notNull(collectorType, "collectorType must not be null");
        return collectors.get(collectorType);
    }

    @Override
    public void addListenerCache(ListenerCacheKey cacheKey, List<EventListener> eventListeners) {
        Assert.notNull(cacheKey, "cacheKey must not be null");
        Assert.notEmpty(eventListeners, "eventListeners must not be null");
        this.listenerCache.put(cacheKey, eventListeners);
    }

    @Override
    public List<EventListener> getSmartEventListenerFromCache(ListenerCacheKey cacheKey) {
        Assert.notNull(cacheKey, "cacheKey must not be null");
        List<EventListener> listeners = this.listenerCache.get(cacheKey);
        return listeners == null ? Collections.EMPTY_LIST : listeners;
    }

    public InterceptorChain initializesInterceptor(InterceptorChain chain) {
        Objects.requireNonNull(chain, "chain must not be null");
        for (Interceptor plugin : configuration.getPlugins())
            chain.addInterceptor(plugin);
        return chain;
    }

    private void registrationHandlers() {
        this.handlers = new Handlers(executorService, interceptorChain);
    }

    /**
     * getter and setter
     */
    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    Configuration getConfiguration() {
        return configuration;
    }

    public static class ListenerCacheKey {
        private final Class<?> eventType;
        private final Class<?> sourceType;

        public ListenerCacheKey(Class<?> eventType, Class<?> sourceType) {
            this.eventType = eventType;
            this.sourceType = sourceType;
        }

        public static ListenerCacheKey generator(Class<?> eventType, Class<?> sourceType) {
            return new ListenerCacheKey(eventType, sourceType);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ListenerCacheKey that = (ListenerCacheKey) o;

            return ObjectUtils.nullSafeEquals(this.eventType, that.eventType) && ObjectUtils.nullSafeEquals(this.sourceType, that.sourceType);
        }

        @Override
        public int hashCode() {
            return ObjectUtils.nullSafeHashCode(this.eventType) * 29 + ObjectUtils.nullSafeHashCode(this.sourceType);
        }
    }
}
