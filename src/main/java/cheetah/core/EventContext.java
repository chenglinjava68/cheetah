package cheetah.core;

import cheetah.engine.Engine;
import cheetah.handler.Handler;

import java.util.EventListener;
import java.util.Map;

/**
 * Created by Max on 2016/2/21.
 */
public final class EventContext {
    private static final ThreadLocal<Map<Class<? extends EventListener>, Handler>> handlers = new ThreadLocal<>();
    private static final ThreadLocal<EventMessage> eventMessage = new ThreadLocal<>();

    private static final EventContext CONTEXT = new EventContext();
    private EventContext(){}

    static EventContext getContext() {
        return CONTEXT;
    }

    public final void setHandlers(Map<Class<? extends EventListener>, Handler> $handlers) {
        EventContext.handlers.set($handlers);
    }

    public final Map<Class<? extends EventListener>, Handler> getHandlers() {
        return EventContext.handlers.get();
    }

    public final void removeHandlers() {
        EventContext.handlers.remove();
    }

    public final void setEventMessage(EventMessage $eventMessage) {
        EventContext.eventMessage.set($eventMessage);
    }

    public final EventMessage getEventMessage() {
        return EventContext.eventMessage.get();
    }

    public final void removeEventMessage() {
        EventContext.eventMessage.remove();
    }

}
