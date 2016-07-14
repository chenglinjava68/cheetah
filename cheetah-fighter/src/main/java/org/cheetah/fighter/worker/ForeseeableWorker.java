package org.cheetah.fighter.worker;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.cheetah.commons.logger.Loggers;
import org.cheetah.fighter.core.Interceptor;
import org.cheetah.fighter.core.handler.Handler;
import org.cheetah.fighter.core.worker.AbstractWorker;
import org.cheetah.fighter.core.worker.Command;

import java.util.EventListener;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;

/**
 * Created by Max on 2016/3/2.
 */
public class ForeseeableWorker extends AbstractWorker {
    private Map<Class<? extends EventListener>, Handler> handlerMap;
    private ListeningExecutorService executor;
    private List<Interceptor> interceptors;

    @Override
    public void doWork(Command command) {
        final Handler handler = handlerMap.get(command.eventListener());

        try {
            CompletableFuture.supplyAsync(() ->
                            handler.handle(command)
                    , executor).whenComplete((r, e) -> {
                if (r)
                    handler.onSuccess(command);
                else handler.onFailure(command);
            });
        } catch (RejectedExecutionException e) {
            e.printStackTrace();
            Loggers.me().warn(getClass(), "task rejected execute.", e);
            handler.onFailure(command);
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
        this.executor = MoreExecutors.listeningDecorator(executor);
    }

    public void setInterceptors(List<Interceptor> interceptors) {
        this.interceptors = interceptors;
    }
}
