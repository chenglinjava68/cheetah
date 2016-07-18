package org.cheetah.fighter.worker;

import org.cheetah.fighter.core.worker.Worker;
import org.cheetah.fighter.core.worker.WorkerFactory;

/**
 * Created by Max on 2016/2/21.
 */
public class ForeseeableWorkerFactory implements WorkerFactory {

    @Override
    public Worker createWorker() {
        return new ForeseeableWorker();
    }

}
