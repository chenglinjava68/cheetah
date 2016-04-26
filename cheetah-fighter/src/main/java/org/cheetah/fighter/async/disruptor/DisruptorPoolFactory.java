package org.cheetah.fighter.async.disruptor;

import org.cheetah.fighter.async.AsynchronousFactory;
import org.cheetah.fighter.async.AsynchronousPoolFactory;
import org.cheetah.fighter.core.EventContext;
import org.cheetah.fighter.core.NoMapperException;
import org.cheetah.fighter.event.DomainEvent;
import org.cheetah.fighter.mapping.HandlerMapping;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Max on 2016/2/29.
 */
public class DisruptorPoolFactory implements AsynchronousPoolFactory<Disruptor<DisruptorEvent>> {
    private AsynchronousFactory<Disruptor<DisruptorEvent>> disruptorFactory;
    private final Map<HandlerMapping.HandlerMapperKey, Disruptor<DisruptorEvent>> disruptorPool;
    private EventContext context;

    public DisruptorPoolFactory() {
        this.disruptorPool = new ConcurrentHashMap<>();
    }

    public Disruptor<DisruptorEvent> createDisruptor() {
        Disruptor<DisruptorEvent> disruptor = this.disruptorPool.get(HandlerMapping.HandlerMapperKey.generate(context.eventMessage().event()));
        if (Objects.nonNull(disruptor))
            return disruptor;
        if (context.eventMessage().event() instanceof DomainEvent)
            return this.disruptorFactory.createAsynchronous(ProducerType.SINGLE.name(), context.handlers(), context.interceptors());
        else
            return this.disruptorFactory.createAsynchronous(ProducerType.MULTI.name(), context.handlers(),context.interceptors());
    }

    @Override
    public Disruptor<DisruptorEvent> getAsynchronous() {
        Disruptor<DisruptorEvent> disruptor = this.disruptorPool.get(HandlerMapping.HandlerMapperKey.generate(context.eventMessage().event()));
        if (Objects.nonNull(disruptor)) {
            return disruptor;
        } else {
            synchronized (this) {
                if (context.handlers().isEmpty())
                    throw new NoMapperException();
                disruptor = createDisruptor();
                HandlerMapping.HandlerMapperKey key = HandlerMapping.HandlerMapperKey.generate(context.eventMessage().event());
                this.disruptorPool.putIfAbsent(key, disruptor);
                return disruptor;
            }
        }
    }

    @Override
    public void setEventContext(EventContext context) {
        this.context = context;
    }

    @Override
    public void setAsynchronousFactory(AsynchronousFactory asynchronousFactory) {
        this.disruptorFactory = asynchronousFactory;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {
        Map<HandlerMapping.HandlerMapperKey, Disruptor<DisruptorEvent>> stopMap = new HashMap<>(this.disruptorPool);
        this.disruptorPool.clear();
        Iterator<Disruptor<DisruptorEvent>> iterator = stopMap.values().iterator();
        while (iterator.hasNext()) {
            Disruptor<DisruptorEvent> disruptor = iterator.next();
            disruptor.halt();
        }
    }

}
