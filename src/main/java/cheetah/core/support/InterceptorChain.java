package cheetah.core.support;

import cheetah.common.logger.Error;
import cheetah.common.utils.CollectionUtils;
import cheetah.core.Interceptor;
import cheetah.worker.Command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Max on 2016/3/7.
 */
public class InterceptorChain {
    private List<Interceptor> interceptors = new ArrayList<>();
    private int interceptorIndex;

    public boolean startPreHandle(Command command) {
        List<Interceptor> $interceptors = getInterceptors();
        if (!CollectionUtils.isEmpty($interceptors)) {
            for (int i = 0; i < $interceptors.size(); this.interceptorIndex = i++) {
                try {
                    if (!$interceptors.get(i).preHandle(command)) {
                        this.triggerAfterCompletion(command, null);
                        return false;
                    }
                } catch (Exception e) {
                    Error.log(this.getClass(), e.getMessage());
                    this.triggerAfterCompletion(command, e);
                    return false;
                }
            }
        }
        return true;
    }

    public void startPostHandle(Command command) throws Exception {
        List<Interceptor> $interceptors = getInterceptors();
        if (!CollectionUtils.isEmpty($interceptors)) {
            for (int i = 0; i < $interceptors.size(); this.interceptorIndex = --i) {
                $interceptors.get(i).postHandle(command);
            }
        }
    }

    public void triggerAfterCompletion(Command command, Exception ex) {
        List<Interceptor> $interceptors = getInterceptors();
        if (!CollectionUtils.isEmpty($interceptors)) {
            for (int i = 0; i < $interceptors.size(); this.interceptorIndex = --i) {
                try {
                    $interceptors.get(i).afterCompletion(command, ex);
                } catch (Exception e) {
                    Error.log(this.getClass(), "InterceptorChain.afterCompletion threw exception");
                }
            }
        }
    }

    public void register(Interceptor interceptor) {
        interceptors.add(interceptor);
    }

    public List<Interceptor> getInterceptors() {
        return Collections.unmodifiableList(interceptors);
    }

}
