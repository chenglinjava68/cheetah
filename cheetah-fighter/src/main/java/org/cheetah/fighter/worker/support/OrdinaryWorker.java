package org.cheetah.fighter.worker.support;

import org.cheetah.fighter.core.Interceptor;
import org.cheetah.fighter.handler.Directive;
import org.cheetah.fighter.handler.Feedback;
import org.cheetah.fighter.worker.Command;
import org.cheetah.fighter.worker.Worker;
import org.cheetah.fighter.handler.Handler;

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

        Directive directive = new Directive(command.event(), command.callback(), command.needResult());

        CompletableFuture<Feedback> future = CompletableFuture.supplyAsync(() ->
                handler.handle(directive)
                , executor);
        try {
            future.get(3, TimeUnit.SECONDS);
            handler.onSuccess(directive);
        } catch (InterruptedException e) {
            e.printStackTrace();
            handler.onFailure(directive);
        } catch (ExecutionException e) {
            e.printStackTrace();
            handler.onFailure(directive);
        } catch (TimeoutException e) {
            e.printStackTrace();
            handler.onFailure(directive);
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
