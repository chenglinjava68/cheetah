package org.cheetah.fighter.handler.support;

import org.cheetah.fighter.api.DomainEvent;
import org.cheetah.fighter.api.DomainEventListener;
import org.cheetah.fighter.handler.Handler;
import org.cheetah.fighter.handler.HandlerFactory;

/**
 * Created by Max on 2016/2/21.
 */
public class DomainEventHandlerFactory implements HandlerFactory {
    private static final HandlerFactory HANDLER_FACTORY = new DomainEventHandlerFactory();

    public static HandlerFactory getDomainEventHandlerFactory() {
        return HANDLER_FACTORY;
    }

    @Override
    public Handler createDomainEventHandler(DomainEventListener<DomainEvent> eventListener) {
        return new DomainEventHandler(eventListener);
    }

}
