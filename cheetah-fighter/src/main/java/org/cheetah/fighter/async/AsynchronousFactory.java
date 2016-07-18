package org.cheetah.fighter.async;

import org.cheetah.commons.Startable;
import org.cheetah.fighter.core.Interceptor;
import org.cheetah.fighter.core.handler.Handler;

import java.util.EventListener;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * 异步工作者工厂接口
 * Created by Max on 2016/2/29.
 */
public interface AsynchronousFactory<T> extends Startable {
    /**
     * 创建一个异步工作者
     * @param name
     * @param handlers
     * @return
     */
    T createAsynchronous(String name, List<Handler> handlers, List<Interceptor> interceptors);
}
