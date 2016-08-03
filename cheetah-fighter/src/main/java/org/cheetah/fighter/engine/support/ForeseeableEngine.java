package org.cheetah.fighter.engine.support;

import org.cheetah.fighter.engine.AbstractEngine;
import org.cheetah.fighter.worker.Worker;
import org.cheetah.fighter.worker.WorkerAdapter;
import org.cheetah.fighter.worker.support.ForeseeableWorkerAdapter;

/**
 * Created by Max on 2016/2/1.
 */
public class ForeseeableEngine extends AbstractEngine<Worker[]> {

    public ForeseeableEngine() {
        this.state = State.NEW;
    }

    @Override
    public WorkerAdapter assignWorkerAdapter() {
        WorkerAdapter workerAdapter = getWorkerAdapterFactory().createWorkerAdapter();
        ((ForeseeableWorkerAdapter) workerAdapter).setWorkers(getAsynchronous());
        return workerAdapter;
    }

    @Override
    public Worker[] getAsynchronous() {
        return (Worker[]) getAsynchronousPoolFactory().getAsynchronous();
    }

}
