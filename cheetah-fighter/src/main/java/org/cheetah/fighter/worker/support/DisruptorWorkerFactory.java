package org.cheetah.fighter.worker.support;

import org.cheetah.fighter.worker.Worker;
import org.cheetah.fighter.worker.WorkerFactory;

/**
 * Created by Max on 2016/3/2.
 */
public class DisruptorWorkerFactory implements WorkerFactory {
    @Override
    public Worker createWorker() {
        return new DisruptorWorker();
    }
}
