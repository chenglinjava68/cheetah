package org.cheetah.fighter.worker.support;

import com.lmax.disruptor.RingBuffer;
import org.cheetah.fighter.async.disruptor.DisruptorEvent;
import org.cheetah.fighter.worker.WorkerAdapter;
import org.cheetah.fighter.worker.WorkerAdapterFactory;

/**
 * Created by maxhuang on 2016/8/3.
 */
public class DisruptorWorkerAdapterFactory implements WorkerAdapterFactory<RingBuffer<DisruptorEvent>> {

    @Override
    public WorkerAdapter createWorkerAdapter(RingBuffer<DisruptorEvent> ringBuffer) {
        return new DisruptorWorkerAdapter(ringBuffer);
    }
}
