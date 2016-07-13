package org.cheetah.fighter.handler;

import org.cheetah.fighter.core.handler.Handler;
import org.cheetah.fighter.core.handler.HandlerFactory;

/**
 * Created by Max on 2016/2/21.
 */
public class GenericHandlerFactory implements HandlerFactory {
    private static final HandlerFactory HANDLER_FACTORY = new GenericHandlerFactory();

    public static HandlerFactory getGenericHandlerFactory() {
        return HANDLER_FACTORY;
    }

    @Override
    public Handler createDomainEventHandler() {
        return new DomainEventHandler();
    }

}
