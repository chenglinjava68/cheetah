package cheetah.distributor.worker.support;

import cheetah.distributor.worker.AkkaWorker;
import cheetah.distributor.worker.Worker;
import cheetah.distributor.worker.WorkerFactory;

/**
 * Created by Max on 2016/2/21.
 */
public class AkkaWorkerFactory implements WorkerFactory {

    @Override
    public Worker createWorker() {
        return new AkkaWorker();
    }

}
