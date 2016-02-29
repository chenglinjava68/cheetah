package cheetah.worker.support;

import cheetah.async.disruptor.DisruptorEvent;
import cheetah.worker.Command;
import cheetah.worker.Worker;
import com.lmax.disruptor.EventHandler;

/**
 * Created by Max on 2016/2/29.
 */
public class DisruptorWorker implements Worker, EventHandler<DisruptorEvent> {
    @Override
    public void onEvent(DisruptorEvent disruptorEvent, long l, boolean b) throws Exception {

    }

    @Override
    public void work(Command command) {

    }
}
