package org.cheetah.fighter.async.future;

import org.cheetah.fighter.async.AbstractAsynchronousFactory;
import org.cheetah.fighter.core.Interceptor;
import org.cheetah.fighter.core.handler.Handler;
import org.cheetah.fighter.worker.DisruptorWorker;
import org.cheetah.fighter.worker.ForeseeableWorker;

import java.util.Collection;
import java.util.EventListener;
import java.util.List;
import java.util.Map;

/**
 * Created by Max on 2016/3/2.
 */
public class AsyncForeseeableWorkerFactory extends AbstractAsynchronousFactory<ForeseeableWorker[]> {

    @Override
    public ForeseeableWorker[] createAsynchronous(String name, List<Handler> handlers,
                                                List<Interceptor> interceptors) {
        ForeseeableWorker[] workers = new ForeseeableWorker[handlers.size()];
        for (int i = 0; i < handlers.size(); i++) {
            ForeseeableWorker worker = (ForeseeableWorker) getWorkerFactory().createWorker(handlers.get(i), interceptors);
            worker.setExecutor(executorService());
            workers[i] = worker;
        }
        return workers;
    }

}
