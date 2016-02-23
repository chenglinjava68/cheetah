package cheetah.dispatcher.worker.support;

import cheetah.dispatcher.worker.Worker;
import cheetah.dispatcher.worker.WorkerFactory;

/**
 * Created by Max on 2016/2/21.
 */
public class AkkaWorkerFactory implements WorkerFactory {

    @Override
    public Worker createWorker() {
        return new AkkaWorker();
    }

}
