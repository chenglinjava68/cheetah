package org.cheetah.fighter.worker.support;

import org.cheetah.fighter.DomainEvent;
import org.cheetah.fighter.DomainEventListener;
import org.cheetah.fighter.Interceptor;
import org.cheetah.fighter.handler.Handler;
import org.cheetah.fighter.worker.Worker;
import org.cheetah.fighter.worker.WorkerFactory;

import java.util.List;

/**
 * Created by Max on 2016/3/2.
 */
public class DisruptorWorkerFactory implements WorkerFactory {
    @Override
    public Worker createWorker(DomainEventListener<DomainEvent> eventListener, List<Interceptor> interceptors) {
        return new DisruptorWorker(eventListener, interceptors);
    }
}
