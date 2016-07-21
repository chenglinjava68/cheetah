package org.cheetah.fighter;

import com.google.common.collect.Lists;
import com.lmax.disruptor.RingBuffer;
import org.cheetah.commons.Startable;
import org.cheetah.commons.logger.Info;
import org.cheetah.commons.logger.Loggers;
import org.cheetah.commons.utils.CollectionUtils;
import org.cheetah.commons.utils.ObjectUtils;
import org.cheetah.commons.utils.StringUtils;
import org.cheetah.fighter.async.disruptor.DisruptorEvent;
import org.cheetah.fighter.engine.Engine;
import org.cheetah.fighter.engine.EngineDirector;
import org.cheetah.fighter.governor.support.ForeseeableWorkerAdapter;
import org.cheetah.fighter.handler.Handler;
import org.cheetah.fighter.engine.support.EngineStrategy;
import org.cheetah.fighter.mapping.EventHandlerResolver;
import org.cheetah.fighter.plugin.Plugin;
import org.cheetah.fighter.plugin.PluginChain;
import org.cheetah.fighter.worker.Worker;
import org.cheetah.fighter.worker.WorkerAdapter;
import org.cheetah.fighter.worker.support.DisruptorWorkerAdapter;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by Max on 2016/1/29.
 */
public class EventBus implements Dispatcher, Startable {

    private FighterConfig fighterConfig; //框架配置
    private final PluginChain pluginChain = new PluginChain();
    private Engine engine;
    private EngineDirector engineDirector;
    private EngineStrategy engineStrategy;
    private final Map<InterceptorCacheKey, List<Interceptor>> interceptorCache = new ConcurrentHashMap<>();
    private EventHandlerResolver handlerResolver;
    private final EventContext context = EventContext.getContext();

    /**
     * 接收事件消息，安排相应的engine对其进行处理
     *
     * @param eventMessage
     * @return
     */
    @Override
    public EventResult receive(final EventMessage eventMessage) {
        try {
            context().setEventMessage(eventMessage);
            DomainEvent event = eventMessage.event();
            HandlerMapping.HandlerMapperKey key = HandlerMapping.HandlerMapperKey.generate(event.getClass(), event.getSource().getClass());
            List<Interceptor> interceptors = findInterceptor(event);
            context.setInterceptor(interceptors);
            boolean exists = engine.getMapping().isExists(key);
            if (exists) {
                List<Handler> handlerMap = engine.getMapping().getHandlers(key);
                context.setHandlers(handlerMap);
                return dispatch();
            }
            List<Handler> handlers = handlerResolver.getHandlers(event, key);
            if (handlers.isEmpty()) {
                Loggers.me().warn(this.getClass(), "Couldn't find the corresponding mapping.");
                throw new NoMapperException();
            }
            context.setHandlers(handlers);
            return dispatch();
        } finally {
            context.removeEventMessage();
            context.removeHandlers();
            context.removeInterceptor();
        }
    }

    @Override
    public EventResult dispatch() {
        EventMessage eventMessage = context().eventMessage();
        return doDispatch(eventMessage);
    }

    private EventResult doDispatch(EventMessage eventMessage) {
        WorkerAdapter workerAdapter = getWorkerAdapter(this.engineStrategy);
        if(workerAdapter instanceof DisruptorWorkerAdapter)
            ((DisruptorWorkerAdapter) workerAdapter).setRingBuffer((RingBuffer<DisruptorEvent>) engine.getAsynchronous());
        if(workerAdapter instanceof ForeseeableWorkerAdapter)
            ((ForeseeableWorkerAdapter) workerAdapter).setWorkers((Worker[]) engine.getAsynchronous());
        Feedback feedback = workerAdapter.work(eventMessage);
        return new EventResult(eventMessage.event(), feedback.isSuccess());
    }

    @Override
    public void start() {
        if (fighterConfig != null) {
            if (fighterConfig.hasPlugin())
                initializesPlugin(pluginChain);
        } else
            fighterConfig = new FighterConfig();
        if (StringUtils.isEmpty(fighterConfig.getEngine())) {
            engineStrategy = EngineStrategy.DISRUPTOR;
            engineDirector = engineStrategy.getEngineDirector();
        } else {
            engineStrategy = EngineStrategy.getEngine(fighterConfig.getEngine());
            engineDirector = engineStrategy.getEngineDirector();
        }
        engineDirector.setFighterConfig(this.fighterConfig);
        engine = engineDirector.directEngine();
        engine.setContext(this.context());
        engine.registerPluginChain(pluginChain);
        handlerResolver = new EventHandlerResolver(engine, fighterConfig);
        engine.start();
        Info.log(this.getClass(), "EventBus start engine is {}", engineStrategy.name());
    }

    @Override
    public void stop() {
        this.engine.stop();
        this.engine = null;
        engineDirector = null;
        engineStrategy = null;
        Info.log(this.getClass(), "EventBus stop...");
    }

    public PluginChain initializesPlugin(PluginChain chain) {
        Objects.requireNonNull(chain, "chain must not be null");
        for (Plugin plugin : fighterConfig.getPlugins())
            chain.register(plugin);
        return chain;
    }

    private List<Interceptor> findInterceptor(final DomainEvent event) {
        List<Interceptor> interceptors = this.fighterConfig.getInterceptors();
        if (CollectionUtils.isEmpty(interceptors))
            return Collections.emptyList();
        InterceptorCacheKey key = new InterceptorCacheKey(event.getClass());
        List<Interceptor> $interceptors = interceptorCache.get(key);
        if (CollectionUtils.isEmpty($interceptors)) {
            $interceptors = interceptors.stream().filter(o -> o.supportsType(event.getClass())).collect(Collectors.toList());
            interceptorCache.put(key, $interceptors);
        }
        return $interceptors;
    }

    private List<WorkerAdapter> getDefaultWorkerAdapter() {
        return Lists.newArrayList(
                new DisruptorWorkerAdapter(),
                new ForeseeableWorkerAdapter()
        );
    }

    private WorkerAdapter getWorkerAdapter(EngineStrategy engineStrategy) {
        return getDefaultWorkerAdapter()
                .stream()
                .filter(o -> o.supports(engineStrategy))
                .findFirst()
                .get();
    }

    /**
     * getter and setter
     */
    public void setFighterConfig(FighterConfig fighterConfig) {
        this.fighterConfig = fighterConfig;
    }

    protected FighterConfig fighterConfig() {
        return fighterConfig;
    }

    protected PluginChain pluginChain() {
        return pluginChain;
    }

    protected EventContext context() {
        return context;
    }

    protected Engine engine() {
        return engine;
    }

    protected EngineDirector getEngineDirector() {
        return engineDirector;
    }

    protected EngineStrategy getEngineStrategy() {
        return engineStrategy;
    }

    static class InterceptorCacheKey {
        private Class<? extends Event> eventClz;

        public InterceptorCacheKey(Class<? extends Event> eventClz) {
            this.eventClz = eventClz;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            InterceptorCacheKey that = (InterceptorCacheKey) o;

            return ObjectUtils.nullSafeEquals(this.eventClz, that.eventClz);
        }

        @Override
        public int hashCode() {
            return ObjectUtils.nullSafeHashCode(this.eventClz) * 29;
        }
    }

}
