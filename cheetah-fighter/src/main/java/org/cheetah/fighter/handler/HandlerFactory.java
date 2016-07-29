package org.cheetah.fighter.handler;

import org.cheetah.fighter.DomainEvent;
import org.cheetah.fighter.DomainEventListener;

/**
 * Created by Max on 2016/2/21.
 */
public interface HandlerFactory {
    Handler createDomainEventHandler(DomainEventListener<DomainEvent> eventListener);
}
