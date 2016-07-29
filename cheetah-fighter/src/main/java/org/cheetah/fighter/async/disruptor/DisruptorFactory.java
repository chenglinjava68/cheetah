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

import java.util.*;

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
    public Disruptor<DisruptorEvent> createAsynchronous(String name, List<DomainEventListener> eventListeners, List<Interceptor> interceptors) {
        Disruptor<DisruptorEvent> disruptor;
        if (name.equals(ProducerType.SINGLE.name()))
            disruptor = createSingleDisruptor();
        else disruptor = createMultiDisruptor();

        DisruptorWorker[] workers = new DisruptorWorker[eventListeners.size()];
        for (int i = 0; i < eventListeners.size(); i++) {
            DisruptorWorker worker = (DisruptorWorker) getWorkerFactory().createWorker(eventListeners.get(i), interceptors);
            workers[i] = worker;
        }

        disruptor.handleEventsWith(workers);
        disruptor.start();
        return disruptor;
    }

}
