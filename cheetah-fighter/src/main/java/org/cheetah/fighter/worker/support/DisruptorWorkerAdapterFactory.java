package org.cheetah.fighter.worker.support;

import org.cheetah.fighter.worker.WorkerAdapter;
import org.cheetah.fighter.worker.WorkerAdapterFactory;

/**
 * Created by maxhuang on 2016/8/3.
 */
public class DisruptorWorkerAdapterFactory implements WorkerAdapterFactory {
    @Override
    public WorkerAdapter createWorkerAdapter() {
        return new DisruptorWorkerAdapter();
    }
}
