package cheetah.dispatcher.core;

import cheetah.dispatcher.Startable;
import cheetah.dispatcher.engine.Engine;
import cheetah.dispatcher.engine.EngineDirector;
import cheetah.dispatcher.engine.support.EnginePolicy;
import cheetah.dispatcher.event.*;
import cheetah.dispatcher.machine.Machine;
import cheetah.dispatcher.mapper.support.MachineMapper;
import cheetah.dispatcher.mapper.Mapper;
import cheetah.logger.Debug;
import cheetah.plugin.Interceptor;
import cheetah.plugin.InterceptorChain;
import cheetah.util.CollectionUtils;

import java.util.EventListener;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by Max on 2016/2/23.
 */
public abstract class AbstractDispatcher implements Dispatcher, Startable {

    private Configuration configuration;
    private final InterceptorChain interceptorChain = new InterceptorChain();
    private Engine engine;
    private EnginePolicy policy;
    private EngineDirector engineDirector;
    private Mapper mapper;

    public AbstractDispatcher() {
        this.policy = EnginePolicy.DEFAULT;
        this.mapper = new MachineMapper();
    }

    @Override
    public EventResult receive(EventMessage eventMessage) {
        Event event = eventMessage.getEvent();
        Mapper.MachineMapperKey key = Mapper.MachineMapperKey.generate(event.getClass(), event.getSource().getClass());

        boolean exists = mapper.isExists(key);
        if (exists)
            return dispatch(eventMessage);
        convertMapper(event, key);
        return dispatch(eventMessage);
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

    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
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

    protected Mapper getMapper() {
        return mapper;
    }

    /**
     * Mapper helper
     *
     * @param event
     */
    private void convertMapper(Event event, Mapper.MachineMapperKey mapperKey) {
        if (DomainEvent.class.isAssignableFrom(event.getClass())) {
            Debug.log(this.getClass(), "event is DomainEvent");
            List<EventListener> smartDomainEventListeners = getSmartDomainEventListener(event);
            if (!smartDomainEventListeners.isEmpty()) {
                setDomainEventListenerMapper(mapperKey, smartDomainEventListeners);
                Debug.log(this.getClass(), "has " + smartDomainEventListeners.size() + " listener");
            } else {
                List<EventListener> listeners = this.configuration.getEventListeners().
                        stream().filter(eventListener ->
                        CollectionUtils.arrayToList(eventListener.getClass().getInterfaces())
                                .contains(DomainEventListener.class)).collect(Collectors.toList());
                setDomainEventListenerMapper(mapperKey, listeners);
                Debug.log(this.getClass(), "has " + smartDomainEventListeners.size() + " listener");
            }
        } else if (ApplicationEvent.class.isAssignableFrom(event.getClass())) {
            Debug.log(this.getClass(), "event is ApplicationEvent");
            List<EventListener> smartAppEventListeners = getSmartApplicationEventListener(event);
            if (!smartAppEventListeners.isEmpty()) {
                setAppEventListenerMapper(mapperKey, smartAppEventListeners);
                Debug.log(this.getClass(), "has " + smartAppEventListeners.size() + " listener");
            } else {
                List<EventListener> listeners = this.configuration.getEventListeners().
                        stream().filter(eventListener ->
                        CollectionUtils.arrayToList(eventListener.getClass().getInterfaces())
                                .contains(ApplicationListener.class)).collect(Collectors.toList());
                setAppEventListenerMapper(mapperKey, listeners);
                Debug.log(this.getClass(), "has " + smartAppEventListeners.size() + " listener");
            }
        } else throw new ErrorEventTypeException();
    }

    private void setAppEventListenerMapper(Mapper.MachineMapperKey mapperKey, List<EventListener> listeners) {
        List<Machine> machines = listeners.stream().
                map(o -> {
                    Machine machine = engine.assignApplicationEventMachine();
                    machine.setEventListener(o);
                    return machine;
                })
                .collect(Collectors.toList());
        this.mapper.put(mapperKey, machines);
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

    private void setDomainEventListenerMapper(Mapper.MachineMapperKey mapperKey,
                                              List<EventListener> listeners) {
        List<Machine> machines = listeners.stream().
                map(o -> {
                    Machine machine = engine.assignDomainEventMachine();
                    machine.setEventListener(o);
                    return machine;
                })
                .collect(Collectors.toList());
        this.mapper.put(mapperKey, machines);
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
