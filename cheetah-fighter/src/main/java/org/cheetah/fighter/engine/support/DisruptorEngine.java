package org.cheetah.fighter.engine.support;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import org.cheetah.fighter.async.disruptor.DisruptorEvent;
import org.cheetah.fighter.engine.AbstractEngine;
import org.cheetah.fighter.worker.WorkerAdapter;
import org.cheetah.fighter.worker.WorkerAdapterFactory;
import org.cheetah.fighter.worker.support.DisruptorWorkerAdapter;
import org.cheetah.fighter.worker.support.DisruptorWorkerAdapterFactory;

/**
 * Created by Max on 2016/2/29.
 */
public class DisruptorEngine extends AbstractEngine<Disruptor<DisruptorEvent>> {

    @Override
    public WorkerAdapter assignWorkerAdapter() {
        RingBuffer ringBuffer = getAsynchronous().getRingBuffer();

        return ((DisruptorWorkerAdapterFactory)getWorkerAdapterFactory()).createWorkerAdapter(ringBuffer);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Disruptor<DisruptorEvent> getAsynchronous() {
        return (Disruptor<DisruptorEvent>) getAsynchronousPoolFactory().getAsynchronous();
    }

}
