package cheetah.async.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * Created by Max on 2016/2/29.
 */
public class DisruptorEventFactory implements EventFactory<DisruptorEvent> {
    @Override
    public DisruptorEvent newInstance() {
        return new DisruptorEvent();
    }
}
