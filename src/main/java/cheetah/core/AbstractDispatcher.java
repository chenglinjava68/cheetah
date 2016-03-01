package cheetah.core;

import cheetah.common.Startable;
import cheetah.common.logger.Debug;
import cheetah.engine.Engine;
import cheetah.engine.EngineDirector;
import cheetah.engine.support.EnginePolicy;
import cheetah.event.*;
import cheetah.machine.Machine;
import cheetah.mapper.Mapper;
import cheetah.plugin.Interceptor;
import cheetah.plugin.InterceptorChain;
import cheetah.util.CollectionUtils;

import java.util.EventListener;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * Created by Max on 2016/2/23.
 */
public abstract class AbstractDispatcher implements Dispatcher, Startable {

    private Configuration configuration; //框架配置
    private final InterceptorChain interceptorChain = new InterceptorChain(); //拦截器链
    private Engine engine;
    private EnginePolicy policy;
    private EngineDirector engineDirector;
    private final ReentrantLock lock = new ReentrantLock();

    public AbstractDispatcher() {
        this.policy = EnginePolicy.DEFAULT;
    }

    @Override
    public EventResult receive(final EventMessage eventMessage) {
        if(!engine.isRunning())
            return null;
        Event event = eventMessage.event();
        Mapper.MachineMapperKey key = Mapper.MachineMapperKey.generate(event.getClass(), event.getSource().getClass());

        boolean exists = engine.getMapper().isExists(key);
        if (exists) {
            return dispatch(eventMessage, engine.getMapper().getMachine(key));
        }
        Map<Class<? extends EventListener>, Machine> machines = convertMapper(event, key);
        return dispatch(eventMessage, machines);
    }

    @Override
    public void start() {
        if (Objects.nonNull(configuration)) {
            if (configuration.hasPlugin())
                initializesInterceptor(interceptorChain);
        } else
            configuration = new Configuration();
        this.engineDirector = policy.getEngineDirector();
        this.engineDirector.setConfiguration(this.configuration);
        this.engine = engineDirector.directEngine();
        engine.start();
    }

    @Override
    public void stop() {
        if (Objects.nonNull(engine))
            engine.stop();
        engine = null;
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

    protected Configuration getConfiguration() {
        return configuration;
    }

    protected InterceptorChain getInterceptorChain() {
        return interceptorChain;
    }

    protected Engine getEngine() {
        return engine;
    }

    protected EnginePolicy getPolicy() {
        return policy;
    }

    protected EngineDirector getEngineDirector() {
        return engineDirector;
    }

    /**
     * Mapper helper
     *
     * @param event
     */
    private Map<Class<? extends EventListener>, Machine> convertMapper(Event event, Mapper.MachineMapperKey mapperKey) {
        lock.lock();
        try {
            if (DomainEvent.class.isAssignableFrom(event.getClass())) {
                Debug.log(this.getClass(), "event is DomainEvent");
                List<EventListener> smartDomainEventListeners = getSmartDomainEventListener(event);
                if (!smartDomainEventListeners.isEmpty()) {
                    return setDomainEventListenerMapper(mapperKey, smartDomainEventListeners);
                } else {
                    List<EventListener> listeners = this.configuration.getEventListeners().
                            stream().filter(eventListener ->
                            CollectionUtils.arrayToList(eventListener.getClass().getInterfaces())
                                    .contains(DomainEventListener.class)).collect(Collectors.toList());
                    return setDomainEventListenerMapper(mapperKey, listeners);
                }
            } else if (ApplicationEvent.class.isAssignableFrom(event.getClass())) {
                Debug.log(this.getClass(), "event is ApplicationEvent");
                List<EventListener> smartAppEventListeners = getSmartApplicationEventListener(event);
                if (!smartAppEventListeners.isEmpty()) {
                    return setAppEventListenerMapper(mapperKey, smartAppEventListeners);
                } else {
                    List<EventListener> listeners = this.configuration.getEventListeners().
                            stream().filter(eventListener ->
                            CollectionUtils.arrayToList(eventListener.getClass().getInterfaces())
                                    .contains(ApplicationListener.class)).collect(Collectors.toList());
                    return setAppEventListenerMapper(mapperKey, listeners);
                }
            } else throw new ErrorEventTypeException();
        } finally {
            lock.unlock();
        }
    }

    private Map<Class<? extends EventListener>, Machine> setAppEventListenerMapper(Mapper.MachineMapperKey mapperKey, List<EventListener> listeners) {
        Map<Class<? extends EventListener>, Machine> machines = listeners.stream().
                map(o -> {
                    Machine machine = engine.assignApplicationEventMachine();
                    machine.setEventListener(o);
                    return machine;
                })
                .collect(Collectors.toMap(o -> o.getEventListener().getClass(), o -> o));
        this.engine.getMapper().put(mapperKey, machines);
        return machines;
    }

    private List<EventListener> getSmartApplicationEventListener(Event event) {
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

    private Map<Class<? extends EventListener>, Machine> setDomainEventListenerMapper(Mapper.MachineMapperKey mapperKey,
                                                                                      List<EventListener> listeners) {
        Map<Class<? extends EventListener>, Machine> machines = listeners.stream().
                map(o -> {
                    Machine machine = engine.assignDomainEventMachine();
                    machine.setEventListener(o);
                    return machine;
                })
                .collect(Collectors.toMap(o -> o.getEventListener().getClass(), o -> o));

        this.engine.getMapper().put(mapperKey, machines);
        return machines;
    }

    private List<EventListener> getSmartDomainEventListener(Event event) {
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
}
