package cheetah.predator.core.support;

import cheetah.commons.utils.CollectionUtils;
import cheetah.predator.core.Interceptor;
import cheetah.predator.core.Session;
import cheetah.predator.protocol.ProtocolConvertor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Max on 2016/3/7.
 */
public class MessageHandlerChain implements Cloneable {
    private List<Interceptor> interceptors;
    private int interceptorIndex;
    private static MessageHandlerChain DEFAULT_CHAIN = new MessageHandlerChain();

    public boolean handle(ProtocolConvertor.Message message, Session session) throws Exception {
        List<Interceptor> $interceptors = getInterceptors();
        if (!CollectionUtils.isEmpty($interceptors)) {
            for (int i = 0; i < $interceptors.size(); this.interceptorIndex = i++) {
                if (!$interceptors.get(i).handle(message, session)) {
                    this.triggerAfterCompletion(message, session, null);
                    return false;
                }
            }
        }
        return true;
    }

    public void triggerAfterCompletion(ProtocolConvertor.Message message, Session session, Exception ex) {
        List<Interceptor> $interceptors = getInterceptors();
        if (!CollectionUtils.isEmpty($interceptors)) {
            for (int i = 0; i < $interceptors.size(); this.interceptorIndex = --i) {
                try {
                    $interceptors.get(i).afterCompletion(message, session, ex);
                } catch (Exception e) {
                    cheetah.commons.logger.Error.log(this.getClass(), "InterceptorChain.afterCompletion threw exception");
                }
            }
        }
    }

    public void register(List<Interceptor> interceptors) {
        this.interceptors.clear();
        this.interceptors.addAll(interceptors);
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

    public static MessageHandlerChain getDefualtChain() {
        return DEFAULT_CHAIN;
    }

    public MessageHandlerChain kagebunsin() throws CloneNotSupportedException {
        MessageHandlerChain chain = (MessageHandlerChain) super.clone();
        chain.reset();
        chain.initialize();
        return chain;
    }

    public static MessageHandlerChain of() throws CloneNotSupportedException {
        return DEFAULT_CHAIN.kagebunsin();
    }
}
