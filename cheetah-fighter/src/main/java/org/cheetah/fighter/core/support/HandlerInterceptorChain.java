package org.cheetah.fighter.core.support;

import org.cheetah.commons.logger.Err;
import org.cheetah.commons.utils.CollectionUtils;
import org.cheetah.fighter.core.Interceptor;
import org.cheetah.fighter.core.worker.Command;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Max on 2016/3/7.
 */
public class HandlerInterceptorChain implements Cloneable {
    private List<Interceptor> interceptors;
    private int interceptorIndex;
    private static HandlerInterceptorChain DEFAULT_CHAIN = new HandlerInterceptorChain();

    public boolean beforeHandle(Command command) throws Exception {
        List<Interceptor> $interceptors = getInterceptors();
        if (!CollectionUtils.isEmpty($interceptors)) {
            for (int i = 0; i < $interceptors.size(); this.interceptorIndex = i++) {
                if (!$interceptors.get(i).preHandle(command)) {
                    this.triggerAfterCompletion(command, null);
                    return false;
                }
            }
        }
        return true;
    }

    public void afterHandle(Command command) throws Exception {
        List<Interceptor> $interceptors = getInterceptors();
        if (!CollectionUtils.isEmpty($interceptors)) {
            for(int i = $interceptors.size() - 1; i >= 0; --i) {
                $interceptors.get(i).postHandle(command);
            }
        }
    }

    public void triggerAfterCompletion(Command command, Exception ex) {
        List<Interceptor> $interceptors = getInterceptors();
        if (!CollectionUtils.isEmpty($interceptors)) {
            for (int i = this.interceptorIndex; i >= 0; --i) {
                try {
                    $interceptors.get(i).afterCompletion(command, ex);
                } catch (Exception e) {
                    Err.log(this.getClass(), "InterceptorChain.afterCompletion threw exception");
                }
            }
        }
    }

    public void register(Interceptor interceptor) {
        interceptors.add(interceptor);
    }

    List<Interceptor> getInterceptors() {
        return interceptors;
    }

    public void initialize() {
        this.interceptorIndex = 0;
        this.interceptors = new ArrayList<>();
    }

    public void reset() {
        this.interceptors = null;
        this.interceptorIndex = 0;
    }

    public static HandlerInterceptorChain getDefualtChain() {
        return DEFAULT_CHAIN;
    }

    public HandlerInterceptorChain kagebunsin() throws CloneNotSupportedException {
        HandlerInterceptorChain chain= (HandlerInterceptorChain) super.clone();
        chain.reset();
        chain.initialize();
        return chain;
    }

    public static HandlerInterceptorChain createChain() throws CloneNotSupportedException {
        return DEFAULT_CHAIN.kagebunsin();
    }
}
