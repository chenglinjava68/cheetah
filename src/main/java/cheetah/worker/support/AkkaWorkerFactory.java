package cheetah.worker.support;

import cheetah.worker.Worker;
import cheetah.worker.WorkerFactory;

/**
 * Created by Max on 2016/2/21.
 */
public class AkkaWorkerFactory implements WorkerFactory {

    @Override
    public Worker createWorker() {
        return new AkkaWorker(null);
    }

}
