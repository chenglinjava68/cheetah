package org.cheetah.fighter.core.worker;

import org.cheetah.commons.logger.Loggers;
import org.cheetah.fighter.core.Interceptor;
import org.cheetah.fighter.core.support.HandlerInterceptorChain;
import org.cheetah.fighter.worker.InterceptorExecutionException;

/**
 * Created by Max on 2016/5/4.
 */
public abstract class AbstractWorker implements Worker {
    /**
     * 根据接受到命令开始工作
     * @param command
     */
    @Override
    public void work(Command command) {
        try {
            HandlerInterceptorChain chain = createInterceptorChain();
            boolean result = chain.beforeHandle(command);
            if (result) {
                doWork(command);
                chain.afterHandle(command);
            }
        } catch (Exception e) {
            Loggers.me().error(this.getClass(), "Worker work fail.", e);
            throw new InterceptorExecutionException(e);
        }
    }

    HandlerInterceptorChain createInterceptorChain() {
        HandlerInterceptorChain chain;
        try {
            chain = HandlerInterceptorChain.createChain();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            chain = new HandlerInterceptorChain();
        }
        for (Interceptor interceptor : getInterceptors())
            chain.register(interceptor);
        return chain;
    }
}
