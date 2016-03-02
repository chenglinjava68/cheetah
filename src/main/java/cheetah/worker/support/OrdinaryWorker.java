package cheetah.worker.support;

import cheetah.handler.Feedback;
import cheetah.handler.Handler;
import cheetah.worker.Command;
import cheetah.worker.Worker;

import java.util.EventListener;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

/**
 * Created by Max on 2016/3/2.
 */
public class OrdinaryWorker implements Worker {
    private Map<Class<? extends EventListener>, Handler> handlerMap;
    private ExecutorService executor;

    @Override
    public void work(Command command) {
        CompletableFuture<Feedback> future = CompletableFuture.supplyAsync(() ->
            handlerMap.get(command.eventListener()).completeExecute(command.event())
        , executor);
        try {
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void setHandlerMap(Map<Class<? extends EventListener>, Handler> handlerMap) {
        this.handlerMap = handlerMap;
    }

    public void setExecutor(ExecutorService executor) {
        this.executor = executor;
    }
}
