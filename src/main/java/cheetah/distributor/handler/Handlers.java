package cheetah.distributor.handler;

import cheetah.distributor.EventMessage;
import cheetah.distributor.EventResult;
import cheetah.event.Event;
import cheetah.exceptions.HandlerTypedNotFoundException;
import cheetah.plugin.InterceptorChain;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

/**
 * Created by Max on 2016/2/1.
 */
public class Handlers {
    private ExecutorService executorService;
    private InterceptorChain interceptorChain;

    public Handlers(ExecutorService executorService, InterceptorChain interceptorChain) {
        this.executorService = executorService;
        this.interceptorChain = interceptorChain;
    }

    public EventResult handle(EventMessage eventMessage, List<EventListener> eventListeners) {
        boolean hasException = true;
        List<Class<? extends EventListener>> exceptionListners = new ArrayList<>();
        for (EventListener listener : eventListeners) {
            if (eventMessage.isNeedResult())
                try {
                    stateful(eventMessage, listener);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    hasException = false;
                    exceptionListners.add(listener.getClass());
                    interceptorChain.pluginAll(eventMessage.getEvent());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    hasException = false;
                    exceptionListners.add(listener.getClass());
                    interceptorChain.pluginAll(eventMessage.getEvent());
                }
            else
                stateless(eventMessage, listener);
        }
        if (eventMessage.isNeedResult())
            return new EventResult(eventMessage.getEvent(), hasException, exceptionListners);
        return new EventResult(eventMessage.getEvent());
    }

    private void stateful(EventMessage eventMessage, EventListener listener) throws ExecutionException, InterruptedException {
        HandlerConglomeration handler = doHandle(eventMessage, listener);
        handler.getFuture().get();
    }

    private void stateless(EventMessage eventMessage, EventListener listener) {
        doHandle(eventMessage, listener);
    }

    private HandlerConglomeration doHandle(EventMessage eventMessage, EventListener listener) {
        HandlerTyped type = HandlerTyped.Manager.convertFrom(listener.getClass());
        HandlerConglomeration handler;
        Event event = eventMessage.getEvent();
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
        handler.handle(event);
        return handler;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public void setInterceptorChain(InterceptorChain interceptorChain) {
        this.interceptorChain = interceptorChain;
    }
}
