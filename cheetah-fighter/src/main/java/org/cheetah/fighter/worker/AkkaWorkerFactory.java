package org.cheetah.fighter.worker;

import org.cheetah.fighter.core.Interceptor;
import org.cheetah.fighter.core.handler.Handler;
import org.cheetah.fighter.core.worker.Worker;
import org.cheetah.fighter.core.worker.WorkerFactory;

import java.util.List;

/**
 * Created by Max on 2016/2/21.
 */
public class AkkaWorkerFactory implements WorkerFactory {

    @Override
    public Worker createWorker(Handler handler, List<Interceptor> interceptors) {
        AkkaWorkerAdaptor adaptor = new AkkaWorkerAdaptor(new AkkaWorker());
        return adaptor;
    }
}
