package org.cheetah.fighter.worker.support;

import org.cheetah.commons.logger.Warn;
import org.cheetah.commons.utils.Objects;
import org.cheetah.fighter.Interceptor;
import org.cheetah.fighter.handler.Handler;
import org.cheetah.fighter.worker.AbstractWorker;
import org.cheetah.fighter.worker.Command;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;

/**
 * Created by Max on 2016/3/2.
 */
public class ForeseeableWorker extends AbstractWorker {
    private ExecutorService executor;

    public ForeseeableWorker(Handler handler, List<Interceptor> interceptors) {
        super(handler, interceptors);
    }

    /**
     * 根据接受到命令开始工作
     *
     * @param command
     */
    @Override
    public void work(Command command) {
        try {
            CompletableFuture.supplyAsync(() ->
                    invoke(command)
                    , executor).whenComplete((r, e) -> {
                if (Objects.nonNull(r) && r)
                    handler.onSuccess(command);
                else handler.onFailure(command, e);
            });
        } catch (RejectedExecutionException e) {
            Warn.log(getClass(), "task rejected execute.", e);
            handler.onFailure(command, e);
        }
    }

    @Override
    protected boolean doWork(Command command) {
        return handler.handle(command);
    }

    @Override
    public List<Interceptor> getInterceptors() {
        return interceptors;
    }

    public void setExecutor(ExecutorService executor) {
        this.executor = executor;
    }

}
