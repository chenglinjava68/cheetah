package cheetah.async.disruptor;

import cheetah.async.AsynchronousFactory;
import cheetah.async.AsynchronousPoolFactory;
import cheetah.core.EventContext;
import cheetah.core.NoMapperException;
import cheetah.mapper.Mapper;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

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
        if(Objects.nonNull(disruptor))
            return disruptor;
        return  this.disruptorFactory.createAsynchronous(ProducerType.MULTI.name(), context.getHandlers());
    }

    @Override
    public Disruptor<DisruptorEvent> getAsynchronous() {
        Disruptor<DisruptorEvent> disruptor = this.disruptorPool.get(Mapper.HandlerMapperKey.generate(context.getEventMessage().event()));
        if (Objects.nonNull(disruptor)) {
            return disruptor;
        } else {
            synchronized (this) {
                if(context.getHandlers().isEmpty())
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

    }

}
