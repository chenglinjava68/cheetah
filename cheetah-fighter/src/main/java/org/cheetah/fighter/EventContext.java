package org.cheetah.fighter;

import org.cheetah.fighter.handler.Handler;

import java.util.List;

/**
 * Created by Max on 2016/3/3.
 */
public final class EventContext {
    /**
     * 本次触发事件中所有的消费者被包装为Handler
     */
    private static final ThreadLocal<List<Handler>> handlers = new ThreadLocal<>();
    /**
     * 配置
     */
    private static final ThreadLocal<FighterConfig> fighterConfig = new ThreadLocal<>();
    /**
     * 本次触发事件的事件消息
     */
    private static final ThreadLocal<EventMessage> eventMessage = new ThreadLocal<>();
    /**
     * 本次触发事件中的拦截器
     */
    private static final ThreadLocal<List<Interceptor>> interceptors = new ThreadLocal<>();

    private static final EventContext CONTEXT = new EventContext();
    private EventContext(){}

    static EventContext getContext() {
        return CONTEXT;
    }

    public final void setHandlers(List<Handler> handlers) {
        EventContext.handlers.set(handlers);
    }

    public final List<Handler> getHandlers() {
        return EventContext.handlers.get();
    }

    public final void removeHandlers() {
        EventContext.handlers.remove();
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
