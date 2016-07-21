package org.cheetah.fighter.api;

import org.cheetah.fighter.EventMessage;
import org.cheetah.fighter.EventBus;
import org.cheetah.fighter.AbstractCollector;
import org.cheetah.fighter.DomainEvent;

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
        getEventBus().receive(new EventMessage(event));
    }

}
