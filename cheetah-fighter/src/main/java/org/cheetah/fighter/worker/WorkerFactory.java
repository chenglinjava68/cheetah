package org.cheetah.fighter.worker;

import org.cheetah.fighter.DomainEvent;
import org.cheetah.fighter.DomainEventListener;
import org.cheetah.fighter.Interceptor;

import java.util.List;

/**
 * Created by Max on 2016/2/21.
 */
public interface WorkerFactory {
    Worker createWorker(DomainEventListener<DomainEvent> eventListener, List<Interceptor> interceptors);
}
