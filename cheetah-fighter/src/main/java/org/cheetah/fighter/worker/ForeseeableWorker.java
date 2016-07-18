package org.cheetah.fighter.worker;

import com.google.common.util.concurrent.Atomics;
import org.cheetah.commons.logger.Loggers;
import org.cheetah.commons.utils.Objects;
import org.cheetah.fighter.core.Interceptor;
import org.cheetah.fighter.core.handler.Handler;
import org.cheetah.fighter.core.support.HandlerInterceptorChain;
import org.cheetah.fighter.core.worker.AbstractWorker;
import org.cheetah.fighter.core.worker.Command;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Max on 2016/3/2.
 */
public class ForeseeableWorker extends AbstractWorker {
    private final AtomicReference<Handler> handler;
    private ExecutorService executor;
    private final List<Interceptor> interceptors;
    static final AtomicLong atomicLong = new AtomicLong();

    public ForeseeableWorker(Handler handler, List<Interceptor> interceptors) {
        this.handler = Atomics.newReference(handler);
        this.interceptors = interceptors;
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
                long start = System.currentTimeMillis();
                System.out.println(atomicLong.incrementAndGet());
                boolean s = doWork(command);
                Loggers.me().debugEnabled(this.getClass(), "work消耗了{}毫秒", System.currentTimeMillis() - start);
                return s;
            }, executor).whenComplete((r, e) -> {
                long start = System.currentTimeMillis();
                if (Objects.nonNull(r) && r)
                    handler.get().onSuccess(command);
                else handler.get().onFailure(command, e);
                Loggers.me().debugEnabled(this.getClass(), "whenComplete消耗了{}毫秒", System.currentTimeMillis() - start);
            });
        } catch (RejectedExecutionException e) {
            Loggers.me().warn(getClass(), "task rejected execute.", e);
            handler.get().onFailure(command, e);
        }
    }

    @Override
    protected boolean doWork(Command command) {
        return invoke(command);
    }

    @Override
    protected boolean invoke(Command command) {
        boolean success = false;
        try {
            HandlerInterceptorChain chain = createInterceptorChain();
            boolean result = chain.beforeHandle(command);
            if (result) {
                success = handler.get().handle(command);
                chain.afterHandle(command);
            }
        } catch (Exception e) {
            Loggers.me().error(this.getClass(), "interceptor invoke Exception", e);
            throw new InterceptorExecutionException(e);
        }
        return success;
    }

    @Override
    public List<Interceptor> getInterceptors() {
        return interceptors;
    }

    public void setExecutor(ExecutorService executor) {
        this.executor = executor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ForeseeableWorker that = (ForeseeableWorker) o;

        if (handler != null ? !handler.equals(that.handler) : that.handler != null) return false;
        if (executor != null ? !executor.equals(that.executor) : that.executor != null) return false;
        return !(interceptors != null ? !interceptors.equals(that.interceptors) : that.interceptors != null);

    }

    @Override
    public int hashCode() {
        int result = handler != null ? handler.hashCode() : 0;
        result = 31 * result + (executor != null ? executor.hashCode() : 0);
        result = 31 * result + (interceptors != null ? interceptors.hashCode() : 0);
        return result;
    }
}
