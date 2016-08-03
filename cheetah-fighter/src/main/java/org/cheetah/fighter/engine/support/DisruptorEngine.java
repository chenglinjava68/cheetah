package org.cheetah.fighter.engine.support;

import com.lmax.disruptor.dsl.Disruptor;
import org.cheetah.fighter.async.disruptor.DisruptorEvent;
import org.cheetah.fighter.engine.AbstractEngine;
import org.cheetah.fighter.worker.WorkerAdapter;
import org.cheetah.fighter.worker.WorkerAdapterFactory;
import org.cheetah.fighter.worker.support.DisruptorWorkerAdapter;

/**
 * Created by Max on 2016/2/29.
 */
public class DisruptorEngine extends AbstractEngine<Disruptor<DisruptorEvent>> {

    @Override
    public WorkerAdapter assignWorkerAdapter() {
        WorkerAdapter workerAdapter = getWorkerAdapterFactory().createWorkerAdapter();
        ((DisruptorWorkerAdapter) workerAdapter).setRingBuffer(getAsynchronous().getRingBuffer());
        return workerAdapter;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Disruptor<DisruptorEvent> getAsynchronous() {
        return (Disruptor<DisruptorEvent>) getAsynchronousPoolFactory().getAsynchronous();
    }

}
