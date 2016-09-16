package org.cheetah.fighter.handler;

import org.cheetah.fighter.DomainEvent;
import org.cheetah.fighter.DomainEventListener;

/**
 * handler工厂
 * Created by Max on 2016/2/21.
 */
public interface HandlerFactory {
    /**
     * 创建一个领域事件的handler
     * @param eventListener
     * @return
     */
    Handler createDomainEventHandler(DomainEventListener<DomainEvent> eventListener);
}
