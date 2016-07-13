package org.cheetah.fighter.worker;

import org.cheetah.fighter.core.worker.Worker;
import org.cheetah.fighter.core.worker.WorkerFactory;

/**
 * Created by Max on 2016/3/2.
 */
public class DisruptorWorkerFactory implements WorkerFactory {
    @Override
    public Worker createWorker() {
        return new DisruptorWorker();
    }
}
