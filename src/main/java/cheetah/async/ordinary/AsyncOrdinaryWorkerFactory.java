package cheetah.async.ordinary;

import cheetah.async.AsynchronousFactory;
import cheetah.handler.Handler;
import cheetah.worker.support.OrdinaryWorker;

import java.util.EventListener;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Max on 2016/3/2.
 */
public class AsyncOrdinaryWorkerFactory implements AsynchronousFactory<OrdinaryWorker> {
    private final Map<String, ExecutorService> executorMap = new ConcurrentHashMap<>();

    @Override
    public OrdinaryWorker createAsynchronous(String name, Map<Class<? extends EventListener>, Handler> handlerMap) {
        OrdinaryWorker worker = new OrdinaryWorker();
        ExecutorService executor = Executors.newCachedThreadPool();
        worker.setExecutor(executor);
        worker.setHandlerMap(handlerMap);
        executorMap.put(name, executor);
        return worker;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {
        executorMap.forEach((k, v) -> {
            while (v.isShutdown()) {
                v.shutdownNow();
            }
        });
    }
}
