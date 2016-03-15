package cheetah.fighter.handler.support;

import cheetah.fighter.event.ApplicationEvent;
import cheetah.fighter.event.ApplicationListener;
import cheetah.fighter.event.Event;
import cheetah.fighter.handler.AbstractHandler;

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
