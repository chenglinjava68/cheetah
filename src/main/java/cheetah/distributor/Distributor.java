package cheetah.distributor;

import cheetah.distributor.handler.Handlers;
import cheetah.event.Event;
import cheetah.event.SmartDomainEventListener;
import cheetah.plugin.Interceptor;
import cheetah.plugin.InterceptorChain;
import cheetah.util.ObjectUtils;

import java.util.EventListener;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Created by Max on 2016/1/29.
 */
public class Distributor implements Startable, Worker {

    private ExecutorService executorService;
    private Configuration configuration;
    private final InterceptorChain interceptorChain = new InterceptorChain();
    private final Map<ListenerCacheKey, List<EventListener>> listenerCache = new ConcurrentHashMap<>();
    private Handlers handlers;

    public Distributor() {
        this.executorService = Executors.newCachedThreadPool();
    }

    @Override
    public void start() {
        if (Objects.nonNull(configuration)) {
            if (configuration.hasPlugin())
                initializesInterceptor(interceptorChain);
        }

        if (Objects.isNull(executorService)) {
            this.executorService = Executors.newCachedThreadPool();
        }

        handlers = new Handlers(this.executorService, this.interceptorChain);
    }

    @Override
    public void stop() {
        while (!executorService.isShutdown()) {
            try {
                executorService.shutdownNow();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        executorService = null;
    }

    @Override
    public EventResult allot(EventMessage eventMessage) {
        Event event = eventMessage.getEvent();
        List<EventListener> smel = getSmartEventListener(event);
        boolean hasCache = !smel.isEmpty();
        if (!hasCache) {
            List<EventListener> eventListeners = this.configuration.getEventListeners().
                    stream().filter(eventListener ->
                    SmartDomainEventListener.class.isAssignableFrom(eventListener.getClass()))
                    .collect(Collectors.toList());
            this.listenerCache.put(ListenerCacheKey.generator(event.getClass(), event.getSource().getClass()), eventListeners);
        }
        smel = getSmartEventListener(event);
        return this.handlers.handle(eventMessage, smel);
    }

    public List<EventListener> getSmartEventListener(Event event) {
        ListenerCacheKey key = ListenerCacheKey.generator(event.getClass(), event.getSource().getClass());
        return this.listenerCache.get(key);
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
    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    private static class ListenerCacheKey {
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
