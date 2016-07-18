package org.cheetah.fighter.handler;

import org.cheetah.commons.logger.Debug;
import org.cheetah.commons.logger.Warn;
import org.cheetah.fighter.core.event.DomainEvent;
import org.cheetah.fighter.core.event.DomainEventListener;
import org.cheetah.fighter.core.event.Event;
import org.cheetah.fighter.core.handler.AbstractHandler;

import java.util.EventListener;

/**
 * Created by Max on 2016/2/1.
 */
public class DomainEventHandler extends AbstractHandler {

    public DomainEventHandler() {
    }

    public DomainEventHandler(DomainEventListener eventListener) {
        super(eventListener);
    }

    protected void doExecute(Event event) {
        Debug.log(this.getClass(), "DomainEventHandler do Execute event {}", event);
        DomainEvent $event = (DomainEvent) event;
        DomainEventListener<DomainEvent> listener = getEventListener();
        listener.onDomainEvent($event);
    }

    /**
     * 有反馈的执行方式
     *
     * @param event
     * @return
     */
    @Override
    protected boolean completeExecute(Event event) {
        try {
            doExecute(event);
        } catch (Throwable e) {
            Warn.log(this.getClass(), "event handler completeExecute error", e);
            return false;
        }
        return true;
    }
}
