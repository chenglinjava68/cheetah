package cheetah.fighter.worker;

import cheetah.fighter.core.Interceptor;
import cheetah.fighter.core.support.HandlerInterceptorChain;
import cheetah.fighter.worker.support.InterceptorExecutionException;

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
            if (result) {
                doWork(command);
                chain.afterHandle(command);
            }
        } catch (Exception e) {
            throw new InterceptorExecutionException(e);
        }
    }

    void doWork(Command command);

    default HandlerInterceptorChain createInterceptorChain() {
        HandlerInterceptorChain chain;
        try {
            chain = HandlerInterceptorChain.createChain();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            chain = new HandlerInterceptorChain();
        }
        getInterceptors().forEach(chain::register);
        return chain;
    }

    List<Interceptor> getInterceptors();
}