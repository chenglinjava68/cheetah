package org.cheetah.fighter.api;

import org.cheetah.fighter.core.EventMessage;
import org.cheetah.fighter.core.support.DispatcherEvent;
import org.cheetah.fighter.event.AbstractCollector;
import org.cheetah.fighter.event.Callback;
import org.cheetah.fighter.event.Event;

/**
 * Created by Max on 2016/2/3.
 */
public class GenericEventCollector extends AbstractCollector {
    public GenericEventCollector() {
    }

    public GenericEventCollector(DispatcherEvent dispatcher) {
        super(dispatcher);
    }

    @Override
    public void collect(Event event) {
        getDispatcher().receive(new EventMessage(event));
    }

    @Override
    public void collect(Event event, Callback callback) {
        getDispatcher().receive(new EventMessage(event, callback));
    }


}
