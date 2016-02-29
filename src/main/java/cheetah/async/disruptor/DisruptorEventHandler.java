package cheetah.async.disruptor;

import cheetah.worker.Command;
import cheetah.worker.Worker;
import com.lmax.disruptor.EventHandler;

import java.util.EventListener;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Max on 2016/2/29.
 */
public class DisruptorEventHandler implements EventHandler<DisruptorEvent>, Worker {
    private Map<Class<? extends EventListener>, List<EventListener>> mapper;

    private static final AtomicLong ATOMIC_LONG = new AtomicLong();
    @Override
    public void onEvent(DisruptorEvent disruptorEvent, long sequence, boolean endOfBatch) throws Exception {
        System.out.println(disruptorEvent.get().toString() + "   =    " + ATOMIC_LONG.incrementAndGet());
    }

    @Override
    public void work(Command command) {
    }

    public void setMapper(Map<Class<? extends EventListener>, List<EventListener>> mapper) {
        this.mapper = mapper;
    }
}
