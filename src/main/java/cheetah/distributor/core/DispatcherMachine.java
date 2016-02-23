package cheetah.distributor.core;

import cheetah.distributor.Startable;
import cheetah.distributor.engine.Engine;
import cheetah.distributor.engine.EngineDirector;
import cheetah.distributor.engine.support.EnginePolicy;
import cheetah.distributor.event.*;
import cheetah.distributor.governor.Governor;
import cheetah.distributor.machine.Feedback;
import cheetah.distributor.machine.Machine;
import cheetah.logger.Debug;
import cheetah.plugin.Interceptor;
import cheetah.plugin.InterceptorChain;
import cheetah.util.Assert;
import cheetah.util.CollectionUtils;
import cheetah.util.ObjectUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by Max on 2016/1/29.
 */
public class DispatcherMachine implements Startable {

    private Configuration configuration;
    private final InterceptorChain interceptorChain = new InterceptorChain();
    private final Map<ListenerCacheKey, List<EventListener>> listenerCache = new ConcurrentHashMap<>();
    private Engine engine;
    private EnginePolicy policy;
    private EngineDirector engineDirector;

    public DispatcherMachine() {
        this.policy = EnginePolicy.DEFAULT;
    }

    @Override
    public void start() {
        if (Objects.nonNull(configuration)) {
            if (configuration.hasPlugin())
                initializesInterceptor(interceptorChain);
        } else
            configuration = new Configuration();
        this.engineDirector = policy.getEngineDirector();
        this.engine = engineDirector.directEngine();
        engine.start();
    }

    @Override
    public void stop() {
        engine.stop();
        listenerCache.clear();
        engine = null;
    }

    public EventResult receive(EventMessage eventMessage) {
        long start = System.currentTimeMillis();
        Event event = eventMessage.getEvent();
        ListenerCacheKey key = ListenerCacheKey.generate(event.getClass(), event.getSource().getClass());
        Engine.MachineCacheKey machineCacheKey = Engine.MachineCacheKey.generate(key);
        List<Machine> machines = this.engine.assignMachineSquad(machineCacheKey);
        List<EventListener> cacheListner = getEventListenerFromCache(key);
        boolean noCache = cacheListner.isEmpty();
        if (noCache && machines.isEmpty())
            setCache(event, key, machineCacheKey);
        machines = this.engine.assignMachineSquad(machineCacheKey);
        if (!machines.isEmpty()) {
            Governor governor = engine.assignGovernor();
            Feedback report = governor.initialize()
                    .setEvent(event)
                    .registerMachineSquad(new ArrayList<>(machines))
                    .setFisrtSucceed(eventMessage.isFisrtWin())
                    .setNeedResult(eventMessage.isNeedResult())
                    .on()
                    .command();
            Debug.log(this.getClass(), "调度花费的毫秒数 : " + (System.currentTimeMillis() - start));
            return new EventResult(event.getSource(), report.isFail());
        } else
            return new EventResult(event.getSource(), Boolean.TRUE);
    }

    private void setCache(Event event, ListenerCacheKey key, Engine.MachineCacheKey machineCacheKey) {
        if (DomainEvent.class.isAssignableFrom(event.getClass())) {
            Debug.log(this.getClass(), "event is DomainEvent");
            List<EventListener> smartDomainEventListeners = getSmartDomainEventListenerCache(event);
            if (!smartDomainEventListeners.isEmpty()) {
                setDomainEventListenerCache(key, machineCacheKey, smartDomainEventListeners);
                Debug.log(this.getClass(), "has " + smartDomainEventListeners.size() + " event");
            } else {
                List<EventListener> listeners = this.configuration.getEventListeners().
                        stream().filter(eventListener ->
                        CollectionUtils.arrayToList(eventListener.getClass().getInterfaces())
                                .contains(DomainEventListener.class)).collect(Collectors.toList());
                setDomainEventListenerCache(key, machineCacheKey, listeners);
                Debug.log(this.getClass(), "has " + smartDomainEventListeners.size() + " event");
            }
        } else if (ApplicationEvent.class.isAssignableFrom(event.getClass())) {
            Debug.log(this.getClass(), "event is ApplicationEvent");
            List<EventListener> smartAppEventListeners = getSmartApplicationEventListenerCache(event);
            if (!smartAppEventListeners.isEmpty()) {
                setAppEventListenerCache(key, machineCacheKey, smartAppEventListeners);
                Debug.log(this.getClass(), "has " + smartAppEventListeners.size() + " event");
            } else {
                List<EventListener> listeners = this.configuration.getEventListeners().
                        stream().filter(eventListener ->
                        CollectionUtils.arrayToList(eventListener.getClass().getInterfaces())
                                .contains(ApplicationListener.class)).collect(Collectors.toList());
                setAppEventListenerCache(key, machineCacheKey, listeners);
                Debug.log(this.getClass(), "has " + smartAppEventListeners.size() + " event");
            }
        } else throw new ErrorEventTypeException();
    }

    private void setAppEventListenerCache(ListenerCacheKey key, Engine.MachineCacheKey machineCacheKey, List<EventListener> listeners) {
        this.listenerCache.put(key, listeners);
        List<Machine> machines = listeners.stream().
                map(o -> {
                    Machine machine = engine.assignApplicationEventMachine();
                    machine.setEventListener(o);
                    return machine;
                })
                .collect(Collectors.toList());
        this.engine.registerMachineSquad(machineCacheKey, machines);
    }

    private List<EventListener> getSmartApplicationEventListenerCache(Event event) {
        return this.configuration.getEventListeners().
                stream().filter(eventListener ->
                SmartApplicationListener.class.isAssignableFrom(eventListener.getClass()))
                .filter(eventListener -> {
                    SmartApplicationListener listener = (SmartApplicationListener) eventListener;
                    boolean supportsEventType = listener.supportsEventType(event.getClass());
                    boolean supportsSourceType = listener.supportsSourceType(event.getSource().getClass());
                    return supportsEventType && supportsSourceType;
                }).map(listener -> (SmartApplicationListener) listener).sorted((o1, o2) ->
                                o1.getOrder() - o2.getOrder()
                ).collect(Collectors.toList());
    }

    private void setDomainEventListenerCache(ListenerCacheKey key, Engine.MachineCacheKey machineCacheKey, List<EventListener> listeners) {
        this.listenerCache.put(key, listeners);
        List<Machine> machines = listeners.stream().
                map(o -> {
                    Machine machine = engine.assignDomainEventMachine();
                    machine.setEventListener(o);
                    return machine;
                })
                .collect(Collectors.toList());
        this.engine.registerMachineSquad(machineCacheKey, machines);
    }

    private List<EventListener> getSmartDomainEventListenerCache(Event event) {
        return this.configuration.getEventListeners().
                stream().filter(eventListener ->
                SmartDomainEventListener.class.isAssignableFrom(eventListener.getClass()))
                .filter(eventListener -> {
                    SmartDomainEventListener listener = (SmartDomainEventListener) eventListener;
                    boolean supportsEventType = listener.supportsEventType(event.getClass());
                    boolean supportsSourceType = listener.supportsSourceType(event.getSource().getClass());
                    return supportsEventType && supportsSourceType;
                }).map(listener -> (SmartDomainEventListener) listener).sorted((o1, o2) ->
                                o1.getOrder() - o2.getOrder()
                ).collect(Collectors.toList());
    }

    public List<EventListener> getEventListenerFromCache(ListenerCacheKey cacheKey) {
        Assert.notNull(cacheKey, "cacheKey must not be null");
        List<EventListener> listeners = this.listenerCache.get(cacheKey);
        return listeners == null ? Collections.EMPTY_LIST : listeners;
    }

    public InterceptorChain initializesInterceptor(InterceptorChain chain) {
        Objects.requireNonNull(chain, "chain must not be null");
        for (Interceptor plugin : configuration.getPlugins())
            chain.addInterceptor(plugin);
        return chain;
    }

    /**
     * getter and setter
     */
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public void setPolicy(String policy) {
        this.policy = EnginePolicy.formatFrom(policy);
    }

    public static class ListenerCacheKey {
        private final Class<?> eventType;
        private final Class<?> sourceType;

        public ListenerCacheKey(Class<?> eventType, Class<?> sourceType) {
            this.eventType = eventType;
            this.sourceType = sourceType;
        }

        public static ListenerCacheKey generate(Class<?> eventType, Class<?> sourceType) {
            return new ListenerCacheKey(eventType, sourceType);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ListenerCacheKey that = (ListenerCacheKey) o;

            return ObjectUtils.nullSafeEquals(this.eventType, that.eventType) && ObjectUtils.nullSafeEquals(this.sourceType, that.sourceType);
        }

        @Override
        public int hashCode() {
            return ObjectUtils.nullSafeHashCode(this.eventType) * 29 + ObjectUtils.nullSafeHashCode(this.sourceType);
        }
    }


}
