package org.cheetah.fighter.api;

import org.cheetah.fighter.core.EventMessage;
import org.cheetah.fighter.core.engine.EventBus;
import org.cheetah.fighter.core.event.AbstractCollector;
import org.cheetah.fighter.core.event.Callback;
import org.cheetah.fighter.core.event.Event;

/**
 * Created by Max on 2016/2/3.
 */
public class DomainEventCollector extends AbstractCollector {
    public DomainEventCollector() {
    }

    public DomainEventCollector(EventBus dispatcher) {
        super(dispatcher);
    }

    @Override
    public void collect(Event event) {
        getEventBus().receive(new EventMessage(event));
    }

    @Override
    public void collect(Event event, Callback callback) {
        getEventBus().receive(new EventMessage(event, callback));
    }


}
