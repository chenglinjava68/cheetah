package org.cheetah.fighter.worker;

import org.cheetah.fighter.Interceptor;
import org.cheetah.fighter.handler.Handler;

import java.util.List;

/**
 * Created by Max on 2016/2/21.
 */
public interface WorkerFactory {
    Worker createWorker(Handler handler, List<Interceptor> interceptors);
}
