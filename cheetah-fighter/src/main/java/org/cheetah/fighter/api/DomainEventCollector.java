package org.cheetah.fighter.api;

import org.cheetah.fighter.*;

import java.util.concurrent.TimeUnit;

/**
 * Created by Max on 2016/2/3.
 */
class DomainEventCollector extends AbstractCollector {
    public DomainEventCollector() {
    }

    public DomainEventCollector(EventBus dispatcher) {
        super(dispatcher);
    }

    @Override
    public void collect(DomainEvent event) {
        getEventBus().dispatch(EventMessage.newBuilder().event(event).build());
    }

    @Override
    public void collect(DomainEvent event, int timeout) {
        getEventBus().dispatch(EventMessage.newBuilder()
                .event(event)
                .timeout(timeout)
                .build());
    }

    @Override
    public EventResult collect(DomainEvent event, boolean feedback) {
        return getEventBus().dispatch(EventMessage.newBuilder()
                .event(event)
                .needResult(feedback)
                .build());
    }

    @Override
    public EventResult collect(DomainEvent event, boolean feedback, int timeout) {
        return null;
    }

    @Override
    public EventResult collect(DomainEvent event, boolean feedback, int timeout, TimeUnit timeUnit) {
        return getEventBus().dispatch(EventMessage.newBuilder()
                .event(event)
                .needResult(feedback)
                .timeout(timeout)
                .timeUnit(timeUnit)
                .build());
    }

}
