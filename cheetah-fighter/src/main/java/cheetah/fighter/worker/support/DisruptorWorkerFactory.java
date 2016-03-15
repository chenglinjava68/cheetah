package cheetah.fighter.worker.support;

import cheetah.fighter.worker.Worker;
import cheetah.fighter.worker.WorkerFactory;

/**
 * Created by Max on 2016/3/2.
 */
public class DisruptorWorkerFactory implements WorkerFactory {
    @Override
    public Worker createWorker() {
        return new DisruptorWorker();
    }
}
