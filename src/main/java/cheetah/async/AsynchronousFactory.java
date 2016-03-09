package cheetah.async;

import cheetah.common.Startable;
import cheetah.core.Interceptor;
import cheetah.handler.Handler;

import java.util.EventListener;
import java.util.List;
import java.util.Map;

/**
 * 异步工作者工厂接口
 * Created by Max on 2016/2/29.
 */
public interface AsynchronousFactory<T> extends Startable {
    /**
     * 创建一个异步工作者
     * @param name
     * @param handlerMap
     * @return
     */
    T createAsynchronous(String name, Map<Class<? extends EventListener>, Handler> handlerMap, List<Interceptor> interceptors);
}
