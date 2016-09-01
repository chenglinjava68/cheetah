package org.cheetah.fighter.api;

import org.cheetah.fighter.handler.Handler;

import java.util.List;

/**
 * Created by Max on 2016/3/3.
 */
public final class EventContext {
    /**
     * 本次触发事件中所有的消费者被包装为Handler
     */
    private final ThreadLocal<List<Handler>> handlers = new ThreadLocal<>();
    /**
     * 配置
     */
    private final ThreadLocal<FighterConfig> fighterConfig = new ThreadLocal<>();
    /**
     * 本次触发事件的事件消息
     */
    private final ThreadLocal<EventMessage> eventMessage = new ThreadLocal<>();
    /**
     * 本次触发事件中的拦截器
     */
    private final ThreadLocal<List<Interceptor>> interceptors = new ThreadLocal<>();

    private static final EventContext CONTEXT = new EventContext();
    private EventContext(){}

    static EventContext getContext() {
        return CONTEXT;
    }

    public final void setHandlers(List<Handler> handlers) {
        this.handlers.set(handlers);
    }

    public final List<Handler> getHandlers() {
        return this.handlers.get();
    }

    public final void removeHandlers() {
        this.handlers.remove();
    }

    public final void setFighterConfig(FighterConfig fighterConfig) {
        this.fighterConfig.set(fighterConfig);
    }

    public final FighterConfig getFighterConfig() {
        return this.fighterConfig.get();
    }

    public final void removeFighterConfig() {
        this.fighterConfig.remove();
    }

    public final void setEventMessage(EventMessage $eventMessage) {
        this.eventMessage.set($eventMessage);
    }

    public final EventMessage getEventMessage() {
        return this.eventMessage.get();
    }

    public final void removeEventMessage() {
        this.eventMessage.remove();
    }

    public final void setInterceptor(List<Interceptor> interceptors) {
        this.interceptors.set(interceptors);
    }

    public final List<Interceptor> getInterceptors() {
        return this.interceptors.get();
    }

    public final void removeInterceptor() {
        this.interceptors.remove();
    }
}
