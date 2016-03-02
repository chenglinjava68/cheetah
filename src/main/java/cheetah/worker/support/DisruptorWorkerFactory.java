package cheetah.worker.support;

import cheetah.worker.Worker;
import cheetah.worker.WorkerFactory;

/**
 * Created by Max on 2016/3/2.
 */
public class DisruptorWorkerFactory implements WorkerFactory {
    @Override
    public Worker createWorker() {
        return new DisruptorWorker();
    }
}
