package org.cheetah.fighter.async.disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.cheetah.fighter.DomainEventListener;
import org.cheetah.fighter.async.AbstractAsynchronousFactory;
import org.cheetah.fighter.Interceptor;
import org.cheetah.fighter.handler.Handler;
import org.cheetah.fighter.worker.support.DisruptorWorker;

import java.util.EventListener;
import java.util.List;
import java.util.Map;

/**
 * Created by Max on 2016/2/29.
 */
public class DisruptorFactory extends AbstractAsynchronousFactory<Disruptor<DisruptorEvent>> {
    private int ringbufferSize = 64;

    public Disruptor<DisruptorEvent> createMultiDisruptor() {
        WaitStrategy waitStrategy = new BlockingWaitStrategy();
        return new Disruptor<>(new DisruptorEventFactory(), ringbufferSize, getExecutorService(), ProducerType.MULTI, waitStrategy);
    }

    public Disruptor<DisruptorEvent> createSingleDisruptor() {
        WaitStrategy waitStrategy = new BlockingWaitStrategy();
        return new Disruptor<>(new DisruptorEventFactory(), ringbufferSize, getExecutorService(), ProducerType.SINGLE, waitStrategy);
    }

    public void setRingbufferSize(int ringbufferSize) {
        this.ringbufferSize = ringbufferSize;
    }

    @Override
    public Disruptor<DisruptorEvent> createAsynchronous(String name, Map<Class<? extends EventListener>, Handler> handlerMap,
                                                        List<Interceptor> interceptors) {
        Disruptor<DisruptorEvent> disruptor;
        if (name.equals(ProducerType.SINGLE.name()))
            disruptor = createSingleDisruptor();
        else disruptor = createMultiDisruptor();
        DisruptorWorker worker = new DisruptorWorker();
        worker.setHandlerMap(handlerMap);
        worker.setInterceptors(interceptors);
        disruptor.handleEventsWith(worker);
        disruptor.start();
        return disruptor;
    }

}
