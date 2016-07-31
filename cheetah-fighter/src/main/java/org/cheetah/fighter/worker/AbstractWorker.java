package org.cheetah.fighter.worker;

import org.cheetah.fighter.HandlerInterceptorChain;
import org.cheetah.fighter.Interceptor;
import org.cheetah.fighter.handler.Handler;

import java.util.List;

/**
 * Created by Max on 2016/5/4.
 */
public abstract class AbstractWorker implements Worker {

    protected final Handler handler;
    protected final List<Interceptor> interceptors;

    public AbstractWorker(Handler handler, List<Interceptor> interceptors) {
        this.handler = handler;
        this.interceptors = interceptors;
    }

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

    protected void invoke(Command command) {
        HandlerInterceptorChain chain = createInterceptorChain();
        boolean result;
        try {
            result = chain.beforeHandle(command);
        } catch (Exception e) {
            throw new InterceptorExecutionException(e);
        }
        if (result)
            doWork(command);
        try {
            chain.afterHandle(command);
        } catch (Exception e) {
            throw new InterceptorExecutionException(e);
        }
    }

    @Override
    public String toString() {
        return "Worker{" +
                "\nhandler=" + handler +
                "\n, interceptors=" + interceptors +
                "\n}";
    }
}
