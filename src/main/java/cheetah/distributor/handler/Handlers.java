package cheetah.distributor.handler;

import cheetah.distributor.EventMessage;
import cheetah.distributor.EventResult;
import cheetah.exceptions.CheetahException;
import cheetah.exceptions.HandlerTypedNotFoundException;
import cheetah.plugin.InterceptorChain;
import cheetah.util.ObjectUtils;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

/**
 * �¼���������-���ݲ�ͬ���¼�������Ӧ�Ĵ�����
 * Created by Max on 2016/2/1.
 */
public class Handlers {
    private final ExecutorService executorService;
    private final InterceptorChain interceptorChain;
    private final Map<HandlerCacheKey, Handler> handlerCacheKeyMap = new HashMap<>();

    public Handlers(ExecutorService executorService, InterceptorChain interceptorChain) {
        this.executorService = executorService;
        this.interceptorChain = interceptorChain;
    }

    /**
     * �¼�������-������Ӧ�ļ�����
     *
     * @param eventMessage
     * @param eventListeners
     * @return
     */
    public EventResult handle(EventMessage eventMessage, List<EventListener> eventListeners) {
        boolean hasException = false;
        List<Class<? extends EventListener>> exceptionListners = new ArrayList<>();
        eventListeners.forEach(listener -> {
            Handler handler = null;

            switch (Handler.ProcessMode.formatFrom(eventMessage.getProcessMode())) {
                case UNIMPEDED:
                    handler = locklessStateless(eventMessage, listener);
                    break;
                case JDK_UNIMPEDED:
                    handler = nativeStateless(eventMessage, listener);
                    break;
                case STATE:
                    try {
                        handler = stateful(eventMessage, listener);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        if (handler != null)
                            handler.removeFuture();
                    }
                    break;
                case STATE_CALL_BACK:
                    try {
                        handler = stateful(eventMessage, listener, ($exception, $eventMessage, exceptionObject, exceptionMessage) -> {
                            interceptorChain.pluginAll(
                                    new CallbackWrapper($exception, $eventMessage, exceptionObject, exceptionMessage));
                        });
                    } finally {
                        if (handler != null)
                            handler.removeFuture();
                    }
                    break;
                default:
                    throw new CheetahException("Process.Mode error, into the default mode");
            }

        });
        return new EventResult(eventMessage.getEvent(), hasException, exceptionListners);
    }

    /**
     * ��״̬���¼������������쳣����Ҫ�Լ��ֶ�����
     *
     * @param eventMessage
     * @param listener
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private Handler stateful(EventMessage eventMessage, EventListener listener) throws ExecutionException, InterruptedException {
        Handler handler = getHandler(listener);
        handler.handle(eventMessage);
        handler.getFuture().get();
        return handler;
    }

    /**
     * ��״̬���¼������Զ������쳣�������ʵ�ֻص��������Բ������쳣���д���
     *
     * @param eventMessage
     * @param listener
     * @param callback
     * @return
     */
    private Handler stateful(EventMessage eventMessage, EventListener listener, HandleCallback callback) {
        Handler handler = getHandler(listener);
        handler.handle(eventMessage, callback);
        return handler;
    }

    /**
     * ��״̬���¼��������������Ĳ��������¼������������¼��������ڲ��������κ��쳣
     *
     * @param eventMessage
     * @param listener
     * @return
     */
    private Handler locklessStateless(EventMessage eventMessage, EventListener listener) {
        Handler handler = getHandler(listener);
        handler.handle(eventMessage, false);
        return handler;
    }

    /**
     * ��״̬���¼���������ԭ��jdk�Ĳ����⴦���¼����������¼��������ڲ��������κ��쳣
     *
     * @param eventMessage
     * @param listener
     * @return
     */
    private Handler nativeStateless(EventMessage eventMessage, EventListener listener) {
        Handler handler = getHandler(listener);
        handler.handle(eventMessage, true);
        return handler;
    }

    private Handler getHandler(EventListener listener) {
        HandlerTyped type = HandlerTyped.Manager.convertFrom(listener.getClass());
        Handler handler = selector(listener, type);
        return handler;
    }

    private Handler selector(EventListener listener, HandlerTyped type) {
        HandlerCacheKey cacheKey = HandlerCacheKey.createKey(type.getClass());
        if (this.handlerCacheKeyMap.containsKey(cacheKey))
            return this.handlerCacheKeyMap.get(cacheKey);
        Handler handler;
        switch (type) {
            case GENERIC:
                handler = new HandlerAdapter(HandlerFactory.createApplicationEventHandler(listener, executorService), interceptorChain);
                break;
            case APP:
                handler = new HandlerAdapter(HandlerFactory.createApplicationEventHandler(listener, executorService), interceptorChain);
                break;
            case DOMAIN:
                handler = new HandlerAdapter(HandlerFactory.createDomainEventHandler(listener, executorService), interceptorChain);
                break;
            case SMART_APP:
                handler = new HandlerAdapter(HandlerFactory.createApplicationEventHandler(listener, executorService), interceptorChain);
                break;
            case SMART_DOMAIN:
                handler = new HandlerAdapter(HandlerFactory.createDomainEventHandler(listener, executorService), interceptorChain);
                break;
            default:
                throw new HandlerTypedNotFoundException();
        }
        this.handlerCacheKeyMap.put(cacheKey, handler);
        return handler;
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
