package org.cheetah.fighter.worker;

import org.cheetah.fighter.core.Interceptor;
import org.cheetah.fighter.core.handler.Handler;
import org.cheetah.fighter.core.worker.Worker;
import org.cheetah.fighter.core.worker.WorkerFactory;

import java.util.List;

/**
 * Created by Max on 2016/2/21.
 */
public class ForeseeableWorkerFactory implements WorkerFactory {

    @Override
    public Worker createWorker(Handler handler, List<Interceptor> interceptors) {
        return new ForeseeableWorker(handler, interceptors);
    }
}
