package cheetah.plugin;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Created by Max on 2016/2/2.
 */
public class PluginConfiguration {
    private List<Interceptor> interceptors;

    public List<Interceptor> getInterceptors() {
        return Collections.unmodifiableList(interceptors);
    }

    public void setInterceptors(List<Interceptor> interceptors) {
        this.interceptors = interceptors;
    }

    public InterceptorChain configuring(InterceptorChain chain) {
        Objects.requireNonNull(chain, "chain must not be null");
        for (Interceptor interceptor : interceptors)
            chain.addInterceptor(interceptor);
        return chain;
    }

}
