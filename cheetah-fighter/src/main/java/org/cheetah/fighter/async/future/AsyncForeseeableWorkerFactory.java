package org.cheetah.fighter.async.future;

import org.cheetah.fighter.async.AbstractAsynchronousFactory;
import org.cheetah.fighter.core.Interceptor;
import org.cheetah.fighter.core.handler.Handler;
import org.cheetah.fighter.worker.ForeseeableWorker;

import java.util.EventListener;
import java.util.List;
import java.util.Map;

/**
 * Created by Max on 2016/3/2.
 */
public class AsyncForeseeableWorkerFactory extends AbstractAsynchronousFactory<ForeseeableWorker> {
    @Override
    public ForeseeableWorker createAsynchronous(String name, Map<Class<? extends EventListener>, Handler> handlerMap,
                                                List<Interceptor> interceptors) {
        ForeseeableWorker worker = new ForeseeableWorker();
        worker.setExecutor(executorService());
        worker.setHandlerMap(handlerMap);
        worker.setInterceptors(interceptors);
        return worker;
    }

}
