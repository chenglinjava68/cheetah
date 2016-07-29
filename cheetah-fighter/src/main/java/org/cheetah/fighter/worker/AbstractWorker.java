package org.cheetah.fighter.worker;

import org.cheetah.commons.logger.Loggers;
import org.cheetah.fighter.DomainEvent;
import org.cheetah.fighter.DomainEventListener;
import org.cheetah.fighter.Interceptor;
import org.cheetah.fighter.HandlerInterceptorChain;
import org.cheetah.fighter.handler.Handler;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Max on 2016/5/4.
 */
public abstract class AbstractWorker implements Worker {

//    protected final Handler handler;
    protected final DomainEventListener<DomainEvent> eventListener;
    protected final List<Interceptor> interceptors;
    protected final AtomicLong counter = new AtomicLong();

    public AbstractWorker(DomainEventListener<DomainEvent> eventListener, List<Interceptor> interceptors) {
        this.eventListener = eventListener;
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
        try {
            HandlerInterceptorChain chain = createInterceptorChain();
            boolean result = chain.beforeHandle(command);
            if (result) {
                doWork(command);
                chain.afterHandle(command);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Loggers.me().error(this.getClass(), "interceptor invoke Exception", e);
        }
    }

}
