package org.cheetah.fighter.async.disruptor;

import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.cheetah.commons.logger.Info;
import org.cheetah.fighter.api.EventBus;
import org.cheetah.fighter.api.EventContext;
import org.cheetah.fighter.async.AsynchronousFactory;
import org.cheetah.fighter.async.AsynchronousPoolFactory;

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
    private final Map<EventBus.HandlerMapperKey, Disruptor<DisruptorEvent>> disruptorPool;
    private EventContext context;

    public DisruptorPoolFactory() {
        this.disruptorPool = new ConcurrentHashMap<>();
    }

    private Disruptor<DisruptorEvent> createDisruptor() {
        Disruptor<DisruptorEvent> disruptor = this.disruptorPool.get(EventBus.HandlerMapperKey.generate(context.getEventMessage().event()));
        if (Objects.nonNull(disruptor))
            return disruptor;
        return this.disruptorFactory.createAsynchronous(ProducerType.SINGLE.name(), context.getHandlers(), context.getInterceptors());
    }

    @Override
    public Disruptor<DisruptorEvent> getAsynchronous() {
        Disruptor<DisruptorEvent> disruptor = this.disruptorPool.get(EventBus.HandlerMapperKey.generate(context.getEventMessage().event()));
        Info.log(this.getClass(), "get asynchronous from pool, {}", disruptor);
        if (Objects.nonNull(disruptor)) {
            return disruptor;
        } else {
            synchronized (this) {
                disruptor = createDisruptor();
                EventBus.HandlerMapperKey key = EventBus.HandlerMapperKey.generate(context.getEventMessage().event());
                this.disruptorPool.putIfAbsent(key, disruptor);
                return this.disruptorPool.get(key);
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
        disruptorFactory.start();
    }

    @Override
    public void stop() {
        Map<EventBus.HandlerMapperKey, Disruptor<DisruptorEvent>> stopMap = new HashMap<>(this.disruptorPool);
        this.disruptorPool.clear();
        Iterator<Disruptor<DisruptorEvent>> iterator = stopMap.values().iterator();
        while (iterator.hasNext()) {
            Disruptor<DisruptorEvent> disruptor = iterator.next();
            disruptor.halt();
            disruptor.shutdown();
        }
        disruptorFactory.stop();
    }

}
