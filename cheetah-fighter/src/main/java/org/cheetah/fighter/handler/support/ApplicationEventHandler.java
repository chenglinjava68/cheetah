package org.cheetah.fighter.handler.support;

import org.cheetah.fighter.event.ApplicationEvent;
import org.cheetah.fighter.event.ApplicationListener;
import org.cheetah.fighter.event.Event;
import org.cheetah.fighter.handler.AbstractHandler;

import java.util.EventListener;

/**
 * Created by Max on 2016/2/1.
 */
public class ApplicationEventHandler extends AbstractHandler {

    public ApplicationEventHandler() {
    }

    public ApplicationEventHandler(EventListener eventListener) {
        super(eventListener);
    }

    protected void doExecute(Event event) {
        ApplicationEvent $event = (ApplicationEvent) event;
        ApplicationListener listener = ((ApplicationListener) this.getEventListener());
        listener.onApplicationEvent($event);
    }

}
