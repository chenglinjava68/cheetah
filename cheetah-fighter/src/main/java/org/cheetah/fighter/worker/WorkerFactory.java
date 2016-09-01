package org.cheetah.fighter.worker;

import org.cheetah.fighter.api.Interceptor;
import org.cheetah.fighter.handler.Handler;

import java.util.List;

/**
 * worker工厂
 * Created by Max on 2016/2/21.
 */
public interface WorkerFactory {
    /**
     * 创建一个worker
     * @param handler
     * @param interceptors
     * @return
     */
    Worker createWorker(Handler handler, List<Interceptor> interceptors);
}
