package cheetah.distributor.core;

import cheetah.distributor.Startable;
import cheetah.distributor.engine.support.DefaultEngineDirector;
import cheetah.distributor.engine.support.DefualtEngineBuilder;
import cheetah.distributor.engine.Engine;
import cheetah.distributor.engine.EngineDirector;
import cheetah.distributor.event.*;
import cheetah.distributor.worker.Worker;
import cheetah.plugin.Interceptor;
import cheetah.plugin.InterceptorChain;
import cheetah.util.Assert;
import cheetah.util.CollectionUtils;
import cheetah.util.ObjectUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by Max on 2016/1/29.
 */
public class DispatcherWorker implements Startable {

    private Configuration configuration;
    private final InterceptorChain interceptorChain = new InterceptorChain();
    private final Map<ListenerCacheKey, List<EventListener>> listenerCache = new ConcurrentHashMap<>();
    private Engine engine;
    private EngineDirector engineDirector;
    private final Map<Class<? extends Event>, Collector> collectors = new ConcurrentHashMap<>();

    public DispatcherWorker() {
        this.engineDirector = new DefaultEngineDirector(new DefualtEngineBuilder());
    }

    @Override
    public void start() {
        if (Objects.nonNull(configuration)) {
            if (configuration.hasPlugin())
                initializesInterceptor(interceptorChain);
        } else
            configuration = new Configuration();
        this.engine = engineDirector.directEngine();
        engine.start();
    }

    @Override
    public void stop() {
        engine.stop();
        listenerCache.clear();
        engine = null;
        engineDirector = null;
        collectors.clear();
    }

    public EventResult receive(Event event) {
        try {
            ListenerCacheKey cacheKey = ListenerCacheKey.generate(event.getClass(), event.getSource().getClass());
            List<EventListener> cacheListner = getEventListenerFromCache(cacheKey);
            ListenerCacheKey key = ListenerCacheKey.generate(event.getClass(), event.getSource().getClass());
            Engine.WorkerCacheKey workerCacheKey = Engine.WorkerCacheKey.generate(key);
            boolean noCache = cacheListner.isEmpty();
            if (noCache)
                setCache(event, key, workerCacheKey);
            List<Worker> workers = this.engine.assignWorkers(workerCacheKey);
            if (!workers.isEmpty()) {
                engine.assignGovernor()
                        .initialize()
                        .setEvent(event)
                        .registerWorker(workers)
                        .command();
                return new EventResult(event.getSource());
            } else
                return new EventResult(event.getSource());
        } finally {
            engine.removeCurrentGovernor();
        }
    }

    private void setCache(Event event, ListenerCacheKey key, Engine.WorkerCacheKey workerCacheKey) {
        if (DomainEvent.class.isAssignableFrom(event.getClass())) {
            List<EventListener> smartDomainEventListeners = getSmartDomainEventListenerCache(event);
            if (!smartDomainEventListeners.isEmpty()) {
                setDomainEventListenerCache(key, workerCacheKey, smartDomainEventListeners);
            } else {
                List<EventListener> listeners = this.configuration.getEventListeners().
                        stream().filter(eventListener ->
                        CollectionUtils.arrayToList(eventListener.getClass().getInterfaces())
                                .contains(DomainEventListener.class)).collect(Collectors.toList());
                setDomainEventListenerCache(key, workerCacheKey, listeners);
            }
        } else if (ApplicationEvent.class.isAssignableFrom(event.getClass())) {
            List<EventListener> smartAppEventListeners = getSmartApplicationEventListenerCache(event);
            if (!smartAppEventListeners.isEmpty()) {
                setAppEventListenerCache(key, workerCacheKey, smartAppEventListeners);
            } else {
                List<EventListener> listeners = this.configuration.getEventListeners().
                        stream().filter(eventListener ->
                        CollectionUtils.arrayToList(eventListener.getClass().getInterfaces())
                                .contains(ApplicationListener.class)).collect(Collectors.toList());
                setAppEventListenerCache(key, workerCacheKey, listeners);

            }
        } else throw new ErrorEventTypeException();
    }

    private void setAppEventListenerCache(ListenerCacheKey key, Engine.WorkerCacheKey workerCacheKey, List<EventListener> listeners) {
        this.listenerCache.put(key, listeners);
        List<Worker> workers = listeners.stream().
                map(o -> {
                    Worker worker = engine.assignApplicationEventWorker();
                    worker.setEventListener(o);
                    worker.setMachinery(engine.assignMachinery());
                    return worker;
                })
                .collect(Collectors.toList());
        this.engine.registerWorkers(workerCacheKey, workers);
    }

    private List<EventListener> getSmartApplicationEventListenerCache(Event event) {
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

    private void setDomainEventListenerCache(ListenerCacheKey key, Engine.WorkerCacheKey workerCacheKey, List<EventListener> listeners) {
        this.listenerCache.put(key, listeners);
        List<Worker> workers = listeners.stream().
                map(o -> {
                    Worker worker = engine.assignDomainEventWorker();
                    worker.setEventListener(o);
                    worker.setMachinery(engine.assignMachinery());
                    return worker;
                })
                .collect(Collectors.toList());
        this.engine.registerWorkers(workerCacheKey, workers);
    }

    private List<EventListener> getSmartDomainEventListenerCache(Event event) {
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

    public void addListenerCache(ListenerCacheKey cacheKey, List<EventListener> eventListeners) {
        Assert.notNull(cacheKey, "cacheKey must not be null");
        Assert.notEmpty(eventListeners, "eventListeners must not be null");
        this.listenerCache.put(cacheKey, eventListeners);
    }

    public List<EventListener> getEventListenerFromCache(ListenerCacheKey cacheKey) {
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

    /**
     * getter and setter
     */
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public void setEngineDirector(EngineDirector engineDirector) {
        this.engineDirector = engineDirector;
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

        public static ListenerCacheKey generate(Class<?> eventType, Class<?> sourceType) {
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
