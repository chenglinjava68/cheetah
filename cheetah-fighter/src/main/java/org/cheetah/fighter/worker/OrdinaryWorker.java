package org.cheetah.fighter.worker;

import com.google.common.util.concurrent.*;
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
public class OrdinaryWorker extends AbstractWorker {
    private Map<Class<? extends EventListener>, Handler> handlerMap;
    private ListeningExecutorService executor;
    private List<Interceptor> interceptors;


    @Override
    public void doWork(Command command) {
        final Handler handler = handlerMap.get(command.eventListener());

        final Directive directive = new Directive(command.event(), command.callback(), command.needResult());

        ListenableFuture future = executor.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                Feedback feedback = handler.handle(directive);
                if (feedback.isFail()) {
                    return false;
                } else return true;
            }
        });

        Futures.addCallback(future, new FutureCallback() {
            @Override
            public void onSuccess(Object o) {

            }

            @Override
            public void onFailure(Throwable throwable) {
                handler.onFailure(directive);
            }
        });
        try {
            future.get(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
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
