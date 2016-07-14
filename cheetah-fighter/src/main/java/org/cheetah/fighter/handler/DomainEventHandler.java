package org.cheetah.fighter.handler;

import org.cheetah.commons.logger.Warn;
import org.cheetah.fighter.core.event.DomainEvent;
import org.cheetah.fighter.core.event.DomainEventListener;
import org.cheetah.fighter.core.event.Event;
import org.cheetah.fighter.core.handler.AbstractHandler;
import org.cheetah.fighter.core.handler.Feedback;

import java.util.EventListener;

/**
 * Created by Max on 2016/2/1.
 */
public class DomainEventHandler extends AbstractHandler {

    public DomainEventHandler() {
    }

    public DomainEventHandler(EventListener eventListener) {
        super(eventListener);
    }

    protected void doExecute(Event event) {
        DomainEvent $event = (DomainEvent) event;
        DomainEventListener<DomainEvent> listener = (DomainEventListener<DomainEvent>) getEventListener();
        listener.onDomainEvent($event);
        listener.onFinish();
    }

    /**
     * 无工作反馈的执行方式
     *
     * @param event
     */
    @Override
    public void execute(Event event) {
        try {
            doExecute(event);
        } catch (Exception e) {
            Warn.log(this.getClass(), "event handler execute error", e);
        }
    }

    /**
     * 有反馈的执行方式
     *
     * @param event
     * @return
     */
    @Override
    public Feedback completeExecute(Event event) {
        try {
            doExecute(event);
        } catch (Throwable e) {
            Warn.log(this.getClass(), "event handler completeExecute error", e);
            return Feedback.FAILURE;
        }
        return Feedback.SUCCESS;
    }

}
