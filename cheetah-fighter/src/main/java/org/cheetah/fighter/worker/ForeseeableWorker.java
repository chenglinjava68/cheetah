package org.cheetah.fighter.worker;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.cheetah.commons.logger.Loggers;
import org.cheetah.fighter.core.Interceptor;
import org.cheetah.fighter.core.handler.Directive;
import org.cheetah.fighter.core.handler.Feedback;
import org.cheetah.fighter.core.handler.Handler;
import org.cheetah.fighter.core.worker.AbstractWorker;
import org.cheetah.fighter.core.worker.Command;

import java.util.EventListener;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

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

        final Directive directive = new Directive(command.event(), command.callback(), command.needResult());

        try {
            CompletableFuture<Boolean> future = CompletableFuture.supplyAsync(() -> {
                Feedback feedback = handler.handle(directive);
                if (feedback.isFail()) {
                    return false;
                } else return true;
            }, executor);
            future.get(3, TimeUnit.SECONDS);
        } catch (RejectedExecutionException e) {
            e.printStackTrace();
            Loggers.me().warn(getClass(), "task rejected execute.", e);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Loggers.me().warn(getClass(), "task interrupted execute.", e);
        } catch (ExecutionException e) {
            e.printStackTrace();
            Loggers.me().warn(getClass(), "task rejected execute.", e);
        } catch (TimeoutException e) {
            e.printStackTrace();
            Loggers.me().warn(getClass(), "task timeout execute.", e);
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
