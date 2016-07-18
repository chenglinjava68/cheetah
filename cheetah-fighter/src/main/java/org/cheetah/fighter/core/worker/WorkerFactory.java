package org.cheetah.fighter.core.worker;

import org.cheetah.fighter.core.Interceptor;
import org.cheetah.fighter.core.handler.Handler;

import java.util.List;

/**
 * Created by Max on 2016/2/21.
 */
public interface WorkerFactory {
    Worker createWorker(Handler handler, List<Interceptor> interceptors);
}
