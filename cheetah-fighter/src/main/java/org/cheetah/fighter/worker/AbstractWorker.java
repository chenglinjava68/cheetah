package org.cheetah.fighter.worker;

import org.cheetah.commons.logger.Loggers;
import org.cheetah.fighter.Interceptor;
import org.cheetah.fighter.HandlerInterceptorChain;
import org.cheetah.fighter.handler.Handler;

import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Created by Max on 2016/5/4.
 */
public abstract class AbstractWorker implements Worker {

    protected Handler handler;
    protected List<Interceptor> interceptors;

    protected abstract boolean doWork(Command command);

    /**
     * 为每个工作者生成一个拦截器链
     * @return
     */
    protected HandlerInterceptorChain createInterceptorChain() {
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

    protected boolean invoke(Command command) {
        boolean success = false;
        try {
            HandlerInterceptorChain chain = createInterceptorChain();
            boolean result = chain.beforeHandle(command);
            if (result) {
                success = doWork(command);
                chain.afterHandle(command);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Loggers.me().error(this.getClass(), "interceptor invoke Exception", e);
        }
        return success;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public void setInterceptors(List<Interceptor> interceptors) {
        this.interceptors = interceptors;
    }
}
