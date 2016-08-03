package org.cheetah.fighter.engine.support;

import org.cheetah.fighter.engine.AbstractEngine;
import org.cheetah.fighter.worker.Worker;
import org.cheetah.fighter.worker.WorkerAdapter;
import org.cheetah.fighter.worker.support.ForeseeableWorkerAdapter;
import org.cheetah.fighter.worker.support.ForeseeableWorkerAdapterFactory;

/**
 * Created by Max on 2016/2/1.
 */
public class ForeseeableEngine extends AbstractEngine<Worker[]> {

    public ForeseeableEngine() {
        this.state = State.NEW;
    }

    @Override
    public WorkerAdapter assignWorkerAdapter() {
        return ((ForeseeableWorkerAdapterFactory)getWorkerAdapterFactory()).createWorkerAdapter(getAsynchronous());
    }

    @Override
    public Worker[] getAsynchronous() {
        return (Worker[]) getAsynchronousPoolFactory().getAsynchronous();
    }

}
