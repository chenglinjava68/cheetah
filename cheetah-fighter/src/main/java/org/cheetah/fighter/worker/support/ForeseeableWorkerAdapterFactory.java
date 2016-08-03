package org.cheetah.fighter.worker.support;

import org.cheetah.fighter.worker.Worker;
import org.cheetah.fighter.worker.WorkerAdapter;
import org.cheetah.fighter.worker.WorkerAdapterFactory;

/**
 * Created by maxhuang on 2016/8/3.
 */
public class ForeseeableWorkerAdapterFactory implements WorkerAdapterFactory<Worker[]> {
    @Override
    public WorkerAdapter createWorkerAdapter(Worker[] workers) {
        return new ForeseeableWorkerAdapter(workers);
    }
}
