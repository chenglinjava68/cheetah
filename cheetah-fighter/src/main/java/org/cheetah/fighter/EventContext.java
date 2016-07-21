package org.cheetah.fighter;

import org.cheetah.fighter.handler.Handler;

import java.util.List;

/**
 * Created by Max on 2016/3/3.
 */
public final class EventContext {
    private static final ThreadLocal<List<Handler>> handlers = new ThreadLocal<>();
    private static final ThreadLocal<EventMessage> eventMessage = new ThreadLocal<>();
    private static final ThreadLocal<List<Interceptor>> interceptors = new ThreadLocal<>();

    private static final EventContext CONTEXT = new EventContext();
    private EventContext(){}

    static EventContext getContext() {
        return CONTEXT;
    }

    public final void setHandlers(List<Handler> $handlers) {
        EventContext.handlers.set($handlers);
    }

    public final List<Handler> handlers() {
        return EventContext.handlers.get();
    }

    public final void removeHandlers() {
        EventContext.handlers.remove();
    }

    public final void setEventMessage(EventMessage $eventMessage) {
        EventContext.eventMessage.set($eventMessage);
    }

    public final EventMessage eventMessage() {
        return EventContext.eventMessage.get();
    }

    public final void removeEventMessage() {
        EventContext.eventMessage.remove();
    }

    public final void setInterceptor(List<Interceptor> interceptors) {
        EventContext.interceptors.set(interceptors);
    }

    public final List<Interceptor> interceptors() {
        return EventContext.interceptors.get();
    }

    public final void removeInterceptor() {
        EventContext.interceptors.remove();
    }
}
