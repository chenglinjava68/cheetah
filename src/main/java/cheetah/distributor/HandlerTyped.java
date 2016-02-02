package cheetah.distributor;

import cheetah.event.ApplicationListener;
import cheetah.event.DomainEventListener;
import cheetah.event.SmartApplicationListener;
import cheetah.event.SmartDomainEventListener;

import java.util.EventListener;

/**
 * Created by Max on 2016/2/2.
 */
public enum HandlerTyped {
    GENERIC {
        @Override
        public boolean from(Class<? extends EventListener> clz) {
            return false;
        }
    }, APP {
        @Override
        public boolean from(Class<? extends EventListener> clz) {
            return clz.isAssignableFrom(ApplicationListener.class);
        }
    }, DOMAIN {
        @Override
        public boolean from(Class<? extends EventListener> clz) {
            return clz.isAssignableFrom(DomainEventListener.class);
        }
    }, SMART_APP {
        @Override
        public boolean from(Class<? extends EventListener> clz) {
            return clz.isAssignableFrom(SmartApplicationListener.class);
        }
    }, SMART_DOMAIN {
        @Override
        public boolean from(Class<? extends EventListener> clz) {
            return clz.isAssignableFrom(SmartDomainEventListener.class);
        }
    };

    public abstract boolean from(Class<? extends EventListener> clz);

    public static class Manager {
        public static HandlerTyped convertFrom(Class<? extends EventListener> clz) {
            for (HandlerTyped type : HandlerTyped.values())
                if (type.from(clz)) return type;
            return GENERIC;
        }
    }
}
