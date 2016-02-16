package cheetah.distributor.handler;

import cheetah.distributor.EventMessage;
import cheetah.distributor.EventResult;
import cheetah.exceptions.HandlerTypedNotFoundException;
import cheetah.plugin.InterceptorChain;
import cheetah.util.ObjectUtils;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

/**
 * Created by Max on 2016/2/1.
 */
public class Handlers {
    private ExecutorService executorService;
    private InterceptorChain interceptorChain;
    private final Map<HandlerCacheKey, Handler> handlerCacheKeyMap = new HashMap<>();

    public Handlers(ExecutorService executorService, InterceptorChain interceptorChain) {
        this.executorService = executorService;
        this.interceptorChain = interceptorChain;
    }

    public EventResult handle(EventMessage eventMessage, List<EventListener> eventListeners) {
        boolean hasException = true;
        List<Class<? extends EventListener>> exceptionListners = new ArrayList<>();
        for (EventListener listener : eventListeners) {
            if (eventMessage.isNeedResult())
                stateful(eventMessage, listener, (exception, $eventMessage, exceptionObject, exceptionMessage) -> {
                    interceptorChain.pluginAll(new HandleCallbackWrap(exception, $eventMessage, exceptionObject, exceptionMessage));
                });
            else
                stateless(eventMessage, listener);
        }
        if (eventMessage.isNeedResult())
            return new EventResult(eventMessage.getEvent(), hasException, exceptionListners);
        return new EventResult(eventMessage.getEvent());
    }

    private void stateful(EventMessage eventMessage, EventListener listener) throws ExecutionException, InterruptedException {
        HandlerTyped type = HandlerTyped.Manager.convertFrom(listener.getClass());
        Handler handler = selector(listener, type);
        handler.handle(eventMessage.getEvent(), true);
        handler.getFuture().get();
    }

    private void stateful(EventMessage eventMessage, EventListener listener, HandleCallback callback) {
        HandlerTyped type = HandlerTyped.Manager.convertFrom(listener.getClass());
        Handler handler = selector(listener, type);
        handler.handle(eventMessage, callback);
    }

    private void stateless(EventMessage eventMessage, EventListener listener) {
        HandlerTyped type = HandlerTyped.Manager.convertFrom(listener.getClass());
        Handler handler = selector(listener, type);
        handler.handle(eventMessage.getEvent(), false);
    }

    private Handler selector(EventListener listener, HandlerTyped type) {
        HandlerCacheKey cacheKey = HandlerCacheKey.createKey(type.getDeclaringClass());
        if (this.handlerCacheKeyMap.containsKey(cacheKey))
            return this.handlerCacheKeyMap.get(cacheKey);
        Handler handler;
        switch (type) {
            case GENERIC:
                handler = new HandlerAdapter(HandlerFactory.createApplicationEventHandler(listener, executorService));
                break;
            case APP:
                handler = new HandlerAdapter(HandlerFactory.createApplicationEventHandler(listener, executorService));
                break;
            case DOMAIN:
                handler = new HandlerAdapter(HandlerFactory.createDomainEventHandler(listener, executorService));
                break;
            case SMART_APP:
                handler = new HandlerAdapter(HandlerFactory.createApplicationEventHandler(listener, executorService));
                break;
            case SMART_DOMAIN:
                handler = new HandlerAdapter(HandlerFactory.createDomainEventHandler(listener, executorService));
                break;
            default:
                throw new HandlerTypedNotFoundException();
        }
        this.handlerCacheKeyMap.put(cacheKey, handler);
        return handler;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public void setInterceptorChain(InterceptorChain interceptorChain) {
        this.interceptorChain = interceptorChain;
    }

    private static class HandlerCacheKey {
        private Class<? extends HandlerTyped> aClass;

        HandlerCacheKey(Class<? extends HandlerTyped> aClass) {
            this.aClass = aClass;
        }

        static HandlerCacheKey createKey(Class<? extends HandlerTyped> keyClz) {
            return new HandlerCacheKey(keyClz);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            HandlerCacheKey that = (HandlerCacheKey) o;

            return ObjectUtils.nullSafeEquals(this.aClass, that.aClass);

        }

        @Override
        public int hashCode() {
            return ObjectUtils.nullSafeHashCode(this.aClass) * 29;
        }
    }
}
