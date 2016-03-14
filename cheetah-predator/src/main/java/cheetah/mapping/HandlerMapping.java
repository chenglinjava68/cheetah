package cheetah.mapping;

import cheetah.common.utils.ObjectUtils;
import cheetah.event.Event;
import cheetah.handler.Handler;

import java.util.EventListener;
import java.util.Map;
import java.util.Set;

/** 处理器映射器
 * Created by Max on 2016/2/23.
 */
public interface HandlerMapping extends Cloneable {

    Map<Class<? extends EventListener>, Handler> getHandlers(HandlerMapperKey mapperKey);

    void put(HandlerMapperKey mapperKey, Map<Class<? extends EventListener>, Handler> machines);

    Set<HandlerMapperKey> mapperKeys();

    boolean isExists(HandlerMapperKey mapperKey);

    class HandlerMapperKey {
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
