package cheetah.handler.support;

import cheetah.core.event.ApplicationEvent;
import cheetah.core.event.ApplicationListener;
import cheetah.core.event.Event;
import cheetah.handler.AbstractHandler;

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
