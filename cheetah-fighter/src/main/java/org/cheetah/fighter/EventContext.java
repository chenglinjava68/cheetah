package org.cheetah.fighter;

import org.cheetah.fighter.handler.Handler;

import java.util.List;

/**
 * Created by Max on 2016/3/3.
 */
public final class EventContext {

    private static final ThreadLocal<List<DomainEventListener>> eventListeners = new ThreadLocal<>();
    private static final ThreadLocal<FighterConfig> fighterConfig = new ThreadLocal<>();
    private static final ThreadLocal<EventMessage> eventMessage = new ThreadLocal<>();
    private static final ThreadLocal<List<Interceptor>> interceptors = new ThreadLocal<>();

    private static final EventContext CONTEXT = new EventContext();
    private EventContext(){}

    static EventContext getContext() {
        return CONTEXT;
    }

    public final void setEventListeners(List<DomainEventListener> eventListeners) {
        EventContext.eventListeners.set(eventListeners);
    }

    public final List<DomainEventListener> getEventListeners() {
        return EventContext.eventListeners.get();
    }

    public final void removeEventListeners() {
        EventContext.eventListeners.remove();
    }

    public final void setFighterConfig(FighterConfig fighterConfig) {
        EventContext.fighterConfig.set(fighterConfig);
    }

    public final FighterConfig getFighterConfig() {
        return EventContext.fighterConfig.get();
    }

    public final void removeFighterConfig() {
        EventContext.fighterConfig.remove();
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

    public final void setInterceptor(List<Interceptor> interceptors) {
        EventContext.interceptors.set(interceptors);
    }

    public final List<Interceptor> getInterceptors() {
        return EventContext.interceptors.get();
    }

    public final void removeInterceptor() {
        EventContext.interceptors.remove();
    }
}
