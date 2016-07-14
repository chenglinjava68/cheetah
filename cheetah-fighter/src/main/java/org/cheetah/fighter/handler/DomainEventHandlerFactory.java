package org.cheetah.fighter.handler;

import org.cheetah.fighter.core.handler.Handler;
import org.cheetah.fighter.core.handler.HandlerFactory;

/**
 * Created by Max on 2016/2/21.
 */
public class DomainEventHandlerFactory implements HandlerFactory {
    private static final HandlerFactory HANDLER_FACTORY = new DomainEventHandlerFactory();

    public static HandlerFactory getDomainEventHandlerFactory() {
        return HANDLER_FACTORY;
    }

    @Override
    public Handler createDomainEventHandler() {
        return new DomainEventHandler();
    }

}
