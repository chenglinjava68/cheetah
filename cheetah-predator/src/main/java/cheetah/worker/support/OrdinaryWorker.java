package cheetah.worker.support;

import cheetah.core.Interceptor;
import cheetah.handler.Feedback;
import cheetah.handler.Handler;
import cheetah.worker.Command;
import cheetah.worker.Worker;

import java.util.EventListener;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by Max on 2016/3/2.
 */
public class OrdinaryWorker implements Worker {
    private Map<Class<? extends EventListener>, Handler> handlerMap;
    private ExecutorService executor;
    private List<Interceptor> interceptors;

    @Override
    public void doWork(Command command) {
        Handler handler = handlerMap.get(command.eventListener());
        CompletableFuture<Feedback> future = CompletableFuture.supplyAsync(() ->
                handler.completeExecute(command.event())
                , executor);
        try {
            future.get(3, TimeUnit.SECONDS);
            handler.onSuccess(command.event());
        } catch (InterruptedException e) {
            e.printStackTrace();
            handler.onFailure(command.event());
        } catch (ExecutionException e) {
            e.printStackTrace();
            handler.onFailure(command.event());
        } catch (TimeoutException e) {
            e.printStackTrace();
            handler.onFailure(command.event());
        }
    }

    @Override
    public List<Interceptor> getInterceptors() {
        return interceptors;
    }

    public void setHandlerMap(Map<Class<? extends EventListener>, Handler> handlerMap) {
        this.handlerMap = handlerMap;
    }

    public void setExecutor(ExecutorService executor) {
        this.executor = executor;
    }

    public void setInterceptors(List<Interceptor> interceptors) {
        this.interceptors = interceptors;
    }
}
