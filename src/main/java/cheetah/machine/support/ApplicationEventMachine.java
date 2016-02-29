package cheetah.machine.support;

import cheetah.event.ApplicationEvent;
import cheetah.event.ApplicationListener;
import cheetah.event.Event;
import cheetah.machine.AbstractMachine;

import java.util.EventListener;

/**
 * Created by Max on 2016/2/1.
 */
public class ApplicationEventMachine extends AbstractMachine {

    public ApplicationEventMachine() {
    }

    public ApplicationEventMachine(EventListener eventListener) {
        super(eventListener);
    }

    protected void doExecute(Event event) {
        ApplicationEvent $event = (ApplicationEvent) event;
        ApplicationListener listener = ((ApplicationListener) this.getEventListener());
        listener.onApplicationEvent($event);
    }

}
