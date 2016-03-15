package cheetah.fighter.async.ordinary;

import cheetah.fighter.async.AsynchronousFactory;
import cheetah.fighter.core.Interceptor;
import cheetah.fighter.handler.Handler;
import cheetah.fighter.worker.support.OrdinaryWorker;

import java.util.EventListener;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Max on 2016/3/2.
 */
public class AsyncOrdinaryWorkerFactory implements AsynchronousFactory<OrdinaryWorker> {
    private ExecutorService executor = Executors.newCachedThreadPool();

    @Override
    public OrdinaryWorker createAsynchronous(String name, Map<Class<? extends EventListener>, Handler> handlerMap,
                                             List<Interceptor> interceptors) {
        OrdinaryWorker worker = new OrdinaryWorker();
        worker.setExecutor(executor);
        worker.setHandlerMap(handlerMap);
        worker.setInterceptors(interceptors);
        return worker;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {
        while (executor.isShutdown()) {
            executor.shutdown();
        }
        executor = null;
    }

    public void setExecutor(ExecutorService executor) {
        this.executor = executor;
    }
}
