package org.cheetah.fighter.async.future;

import org.cheetah.commons.logger.Info;
import org.cheetah.fighter.Interceptor;
import org.cheetah.fighter.async.AbstractAsynchronousFactory;
import org.cheetah.fighter.handler.Handler;
import org.cheetah.fighter.worker.support.ForeseeableWorker;

import java.util.List;

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
            worker.setExecutor(getExecutorService());
            workers[i] = worker;
            Info.log(this.getClass(), "create ForesseableWorker: {}", worker);
        }
        return workers;
    }

}
