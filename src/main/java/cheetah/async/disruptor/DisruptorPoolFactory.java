package cheetah.async.disruptor;

import cheetah.async.AsynchronousFactory;
import cheetah.async.AsynchronousPoolFactory;
import cheetah.core.NoMapperException;
import cheetah.event.DomainEvent;
import cheetah.handler.EventContext;
import cheetah.mapper.Mapper;
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
    private final Map<Mapper.HandlerMapperKey, Disruptor<DisruptorEvent>> disruptorPool;
    private EventContext context;

    public DisruptorPoolFactory() {
        this.disruptorPool = new ConcurrentHashMap<>();
    }

    public Disruptor<DisruptorEvent> createDisruptor() {
        Disruptor<DisruptorEvent> disruptor = this.disruptorPool.get(Mapper.HandlerMapperKey.generate(context.getEventMessage().event()));
        if (Objects.nonNull(disruptor))
            return disruptor;
        if (context.getEventMessage().event() instanceof DomainEvent)
            return this.disruptorFactory.createAsynchronous(ProducerType.SINGLE.name(), context.getHandlers());
        else
            return this.disruptorFactory.createAsynchronous(ProducerType.MULTI.name(), context.getHandlers());
    }

    @Override
    public Disruptor<DisruptorEvent> getAsynchronous() {
        Disruptor<DisruptorEvent> disruptor = this.disruptorPool.get(Mapper.HandlerMapperKey.generate(context.getEventMessage().event()));
        if (Objects.nonNull(disruptor)) {
            return disruptor;
        } else {
            synchronized (this) {
                if (context.getHandlers().isEmpty())
                    throw new NoMapperException();
                disruptor = createDisruptor();
                Mapper.HandlerMapperKey key = Mapper.HandlerMapperKey.generate(context.getEventMessage().event());
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
        Map<Mapper.HandlerMapperKey, Disruptor<DisruptorEvent>> stopMap = new HashMap<>(this.disruptorPool);
        this.disruptorPool.clear();
        Iterator<Disruptor<DisruptorEvent>> iterator = stopMap.values().iterator();
        while (iterator.hasNext()) {
            Disruptor<DisruptorEvent> disruptor = iterator.next();
            disruptor.halt();
        }
    }

}
