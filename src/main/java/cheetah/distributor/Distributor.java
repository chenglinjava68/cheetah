package cheetah.distributor;

import cheetah.event.Event;
import cheetah.plugin.InterceptorChain;
import cheetah.plugin.PluginConfiguration;
import cheetah.util.CollectionUtils;
import cheetah.util.ObjectUtils;

import java.util.EventListener;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Max on 2016/1/29.
 */
public class Distributor implements Startable {
    private List<EventListener> eventListeners;
    private ExecutorService executorService;
    private PluginConfiguration pluginConfiguration;
    private final InterceptorChain interceptorChain = new InterceptorChain();
    private final Map<ListenerCacheKey, List<EventListener>> listenerCache = new ConcurrentHashMap<>();

    public Distributor() {
        this.executorService = Executors.newCachedThreadPool();
    }

    public void divide() {

    }

    public List<EventListener> getSmartEventListener(Event event) {
        ListenerCacheManager.ListenerCacheKey key = ListenerCacheManager.ListenerCacheKey.build(event.getClass(), event.getSource().getClass());
        return this.listenerCache.get(key);
    }

    @Override
    public void start() {
        if (Objects.nonNull(pluginConfiguration) && CollectionUtils.isEmpty(pluginConfiguration.getInterceptors())) {
            pluginConfiguration.configuring(interceptorChain);
        }

        if (Objects.isNull(executorService)) {
            this.executorService = Executors.newCachedThreadPool();
        }
    }

    @Override
    public void stop() {
        while(!executorService.isShutdown()) {
            try {
                executorService.shutdownNow();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        executorService = null;
    }

    public void setEventListeners(List<EventListener> eventListeners) {
        this.eventListeners = eventListeners;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public void setPluginConfiguration(PluginConfiguration pluginConfiguration) {
        this.pluginConfiguration = pluginConfiguration;
    }

    private static class ListenerCacheKey {
        private final Class<?> eventType;
        private final Class<?> sourceType;

        public ListenerCacheKey(Class<?> eventType, Class<?> sourceType) {
            this.eventType = eventType;
            this.sourceType = sourceType;
        }

        public static ListenerCacheKey build(Class<?> eventType, Class<?> sourceType) {
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
