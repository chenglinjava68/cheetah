package org.cheetah.fighter.worker.support;

import org.cheetah.fighter.api.Interceptor;
import org.cheetah.fighter.handler.Handler;
import org.cheetah.fighter.worker.Worker;
import org.cheetah.fighter.worker.WorkerFactory;

import java.util.List;


/**
 * Created by Max on 2016/2/21.
 */
public class AkkaWorkerFactory implements WorkerFactory {

    @Override
    public Worker createWorker(Handler handler, List<Interceptor> interceptors) {
        return new AkkaWorkerAdaptor(new AkkaWorker(handler, interceptors));
    }
}
