package org.cheetah.fighter.async.ordinary;

import org.cheetah.fighter.async.AbstractAsynchronousFactory;
import org.cheetah.fighter.core.Interceptor;
import org.cheetah.fighter.core.handler.Handler;
import org.cheetah.fighter.worker.OrdinaryWorker;

import java.util.EventListener;
import java.util.List;
import java.util.Map;

/**
 * Created by Max on 2016/3/2.
 */
public class AsyncOrdinaryWorkerFactory extends AbstractAsynchronousFactory<OrdinaryWorker> {
    @Override
    public OrdinaryWorker createAsynchronous(String name, Map<Class<? extends EventListener>, Handler> handlerMap,
                                             List<Interceptor> interceptors) {
        OrdinaryWorker worker = new OrdinaryWorker();
        worker.setExecutor(executorService());
        worker.setHandlerMap(handlerMap);
        worker.setInterceptors(interceptors);
        return worker;
    }

}
