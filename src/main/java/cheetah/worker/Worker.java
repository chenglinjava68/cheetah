package cheetah.worker;

import cheetah.core.Interceptor;
import cheetah.core.support.HandlerInterceptorChain;
import cheetah.worker.support.InterceptorExecutionException;

import java.util.List;

/**
 * 事件工作者
 * Created by Max on 2016/2/19.
 */
public interface Worker extends Cloneable {
    /**
     * 根据接受到命令开始工作
     * @param command
     */
    default void work(Command command) {
        try {
            HandlerInterceptorChain chain = createInterceptorChain();
            boolean result = chain.beforeHandle(command);
            if (!result)
                return;
            doWork(command);
            chain.afterHandle(command);
        } catch (Exception e) {
            throw new InterceptorExecutionException(e);
        }
    }

    void doWork(Command command);

    default HandlerInterceptorChain createInterceptorChain() {
        HandlerInterceptorChain chain = new HandlerInterceptorChain();
        getInterceptors().forEach(chain::register);
        return chain;
    }

    List<Interceptor> getInterceptors();
}
