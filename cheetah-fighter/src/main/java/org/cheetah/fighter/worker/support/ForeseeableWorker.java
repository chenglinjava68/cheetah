package org.cheetah.fighter.worker.support;

import org.cheetah.commons.logger.Loggers;
import org.cheetah.commons.logger.Warn;
import org.cheetah.commons.utils.Objects;
import org.cheetah.fighter.*;
import org.cheetah.fighter.handler.Handler;
import org.cheetah.fighter.worker.AbstractWorker;
import org.cheetah.fighter.worker.Command;
import org.cheetah.fighter.worker.InterceptorExecutionException;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Max on 2016/3/2.
 */
public class ForeseeableWorker extends AbstractWorker {
    private ExecutorService executor;

    public ForeseeableWorker(DomainEventListener<DomainEvent> eventListener, List<Interceptor> interceptors) {
        super(eventListener, interceptors);
    }


    /**
     * 根据接受到命令开始工作
     *
     * @param command
     */
    @Override
    public void work(Command command) {
        try {
            CompletableFuture.supplyAsync(() -> {
                System.out.println(counter.incrementAndGet());
                long start = System.nanoTime();
//                boolean s = doWork(command);
//                    Handler h = handler.kagebunsin();
                eventListener.onDomainEvent(command.event());
                boolean s = true;
                Loggers.me().debugEnabled(this.getClass(), "work消耗了{}微秒", System.nanoTime() - start);
                return s;
            }, executor).whenComplete((r, e) -> {
                if (Objects.nonNull(r) && r)
                    eventListener.onFinish(command.event());
                else eventListener.onCancelled(command.event(), e);
            });
        } catch (RejectedExecutionException e) {
            Warn.log(getClass(), "task rejected execute.", e);
            eventListener.onCancelled(command.event(), e);
        }
    }

    @Override
    protected boolean doWork(Command command) {
        try {
            invoke(command);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    protected void invoke(Command command) {
        try {
            HandlerInterceptorChain chain = createInterceptorChain();
            boolean result = chain.beforeHandle(command);
            if (result) {
                eventListener.onDomainEvent(command.event());
                chain.afterHandle(command);
            }
        } catch (Exception e) {
            Loggers.me().error(this.getClass(), "interceptor invoke Exception", e);
            throw new InterceptorExecutionException(e);
        }
    }

    @Override
    public List<Interceptor> getInterceptors() {
        return interceptors;
    }

    public void setExecutor(ExecutorService executor) {
        this.executor = executor;
    }

}
