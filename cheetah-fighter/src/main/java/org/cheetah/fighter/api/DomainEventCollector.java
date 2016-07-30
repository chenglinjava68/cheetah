package org.cheetah.fighter.api;

import org.cheetah.fighter.*;

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
        getEventBus().dispatch(new EventMessage(event));
    }

    @Override
    public EventResult collect(DomainEvent event, boolean feedback) {
       return getEventBus().dispatch(new EventMessage(feedback, event));
    }

}
