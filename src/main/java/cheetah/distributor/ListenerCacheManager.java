package cheetah.distributor;

import cheetah.util.ObjectUtils;

import java.util.EventListener;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Max on 2016/1/29.
 */
public final class ListenerCacheManager {
    private final Map<ListenerCacheKey, List<EventListener>> listenerCache = new ConcurrentHashMap<>();

    public final void put(ListenerCacheKey cacheKey, List<EventListener> eventListeners) {
        this.listenerCache.put(cacheKey, eventListeners);
    }

    public final List<EventListener> get(ListenerCacheKey cacheKey) {
        return this.listenerCache.get(cacheKey);
    }

    public final void remove(ListenerCacheKey cacheKey) {
        this.listenerCache.remove(cacheKey);
    }

    public final void clear() {
        this.listenerCache.clear();
    }

    static class ListenerCacheKey {
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

