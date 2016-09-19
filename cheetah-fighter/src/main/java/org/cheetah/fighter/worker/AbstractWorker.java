package org.cheetah.fighter.worker;

import org.cheetah.fighter.handler.support.HandlerInterceptorChain;
import org.cheetah.fighter.Interceptor;
import org.cheetah.fighter.handler.Handler;

import java.util.List;
import java.util.Objects;

/**
 * 异步工作者的抽象类
 * Created by Max on 2016/5/4.
 */
public abstract class AbstractWorker implements Worker {
    /**
     * 消费者包装类
     */
    protected final Handler handler;
    /**
     * handler拦截器
     */
    protected final List<Interceptor> interceptors;

    public AbstractWorker(Handler handler, List<Interceptor> interceptors) {
        this.handler = handler;
        this.interceptors = interceptors;
    }

    /**
     * 消费者工作函数
     * @param command
     * @return
     */
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

    /**
     * 创建调用连
     * @param command
     */
    protected void invoke(Command command) {
        HandlerInterceptorChain chain = createInterceptorChain();
        Exception executionEx = null;
        boolean preHandleStatus = true;

        try {
            preHandleStatus = preHandle(command, chain);
            if (!preHandleStatus)
                return ;
            doWork(command);
            afterHandle(command, chain);
        } catch (Exception e) {
            executionEx = e;
            throw e;
        } finally {
            if(Objects.isNull(executionEx) && !preHandleStatus)
                ;
            else
                triggerAfterCompletion(command, executionEx, chain);

        }
    }

    /**
     *  消费者执行完毕后，先调用所有拦截器的prehandle
     * @param command
     * @param chain
     */
    protected void afterHandle(Command command, HandlerInterceptorChain chain) {
        try {
            chain.afterHandle(command);
        } catch (Exception e) {
            throw new InterceptorExecutionException(e);
        }
    }

    /**
     *  消费者执行前，先调用所有拦截器的prehandle
     * @param command
     * @param chain
     * @return
     */
    protected boolean preHandle(Command command, HandlerInterceptorChain chain) {
        boolean result;
        try {
            result = chain.beforeHandle(command);
        } catch (Exception e) {
            throw new InterceptorExecutionException(e);
        }
        return result;
    }

    /**
     * prehandle执行返回false时执行触发方法， 如果消费者执行异常也会触发一下方法
     * @param command
     * @param exception
     * @param chain
     */
    protected void triggerAfterCompletion(Command command, Exception exception,HandlerInterceptorChain chain) {
        chain.triggerAfterCompletion(command, exception);
    }


    @Override
    public String toString() {
        return "Worker{" +
                "\nhandler=" + handler +
                "\n, interceptors=" + interceptors +
                "\n}";
    }
}
