package org.cheetah.fighter.worker.support;

import org.cheetah.fighter.core.Interceptor;
import org.cheetah.fighter.handler.Directive;
import org.cheetah.fighter.handler.Feedback;
import org.cheetah.fighter.handler.Handler;
import org.cheetah.fighter.worker.AbstractWorker;
import org.cheetah.fighter.worker.Command;

import java.util.EventListener;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by Max on 2016/3/2.
 */
public class OrdinaryWorker extends AbstractWorker {
    private Map<Class<? extends EventListener>, Handler> handlerMap;
    private ExecutorService executor;
    private List<Interceptor> interceptors;

    @Override
    public void doWork(Command command) {
        final Handler handler = handlerMap.get(command.eventListener());

        final Directive directive = new Directive(command.event(), command.callback(), command.needResult());

        Future<Boolean> future = executor.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                Feedback feedback = handler.handle(directive);
                if (feedback.isFail()) {
                    return false;
                } else return true;
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
        this.executor = executor;
    }

    public void setInterceptors(List<Interceptor> interceptors) {
        this.interceptors = interceptors;
    }
}
