package cheetah.dispatcher.machine;

import cheetah.dispatcher.event.*;
import cheetah.util.CollectionUtils;

import java.util.EventListener;
import java.util.List;

/**
 * Created by Max on 2016/2/2.
 */
public enum HandlerTyped {
    GENERIC {
        @Override
        public boolean from(Class<? extends EventListener> clz) {
            return Event.class.isAssignableFrom(clz);
        }

        @Override
        public int order() {
            return 5;
        }
    }, APP {
        @Override
        public boolean from(Class<? extends EventListener> clz) {
            return ApplicationListener.class.isAssignableFrom(clz);
        }

        @Override
        public int order() {
            return 4;
        }
    }, DOMAIN {
        @Override
        public boolean from(Class<? extends EventListener> clz) {
            return DomainEventListener.class.isAssignableFrom(clz);
        }

        @Override
        public int order() {
            return 3;
        }
    }, SMART_APP {
        @Override
        public boolean from(Class<? extends EventListener> clz) {
            return SmartApplicationListener.class.isAssignableFrom(clz);
        }

        @Override
        public int order() {
            return 2;
        }
    }, SMART_DOMAIN {
        @Override
        public boolean from(Class<? extends EventListener> clz) {
            return SmartDomainEventListener.class.isAssignableFrom(clz);
        }

        @Override
        public int order() {
            return 1;
        }
    };

    public abstract boolean from(Class<? extends EventListener> clz);

    public abstract int order();

    public static class Manager {
        private static List<HandlerTyped> typedCache;

        static {
            typedCache = CollectionUtils.arrayToList(HandlerTyped.values());
            typedCache.sort((o1, o2) -> o1.order() - o2.order());
        }

        public static HandlerTyped convertFrom(Class<? extends EventListener> clz) {
            for (HandlerTyped type : typedCache)
                if (type.from(clz)) return type;
            return GENERIC;
        }
    }
}
