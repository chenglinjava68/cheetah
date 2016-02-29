package cheetah.async.disruptor;

import cheetah.event.Event;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;

/**
 * Created by Max on 2016/2/29.
 */
public class DisruptorFactory {
    private int ringbufferSize = 8;
    protected final ConcurrentHashMap<String, TreeSet<DisruptorEventHandler>> handlesMap = new ConcurrentHashMap<>();

    public Disruptor<DisruptorEvent> createDisruptor() {
        return new Disruptor<>(new DisruptorEventFactory(), ringbufferSize, Executors.newCachedThreadPool());
    }

    public Disruptor<DisruptorEvent> createSingleDisruptor() {
        WaitStrategy waitStrategy = new BlockingWaitStrategy();
        return new Disruptor<>(new DisruptorEventFactory(), ringbufferSize, Executors.newCachedThreadPool(), ProducerType.SINGLE, waitStrategy);
    }

    public void setRingbufferSize(int ringbufferSize) {
        this.ringbufferSize = ringbufferSize;
    }

    static class Translator implements EventTranslatorOneArg<DisruptorEvent, Event> {
        @Override
        public void translateTo(DisruptorEvent event, long sequence, Event data) {
            event.set(data);
        }
    }
}
