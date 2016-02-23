package cheetah.distributor.machine.support;

import cheetah.distributor.event.ApplicationEvent;
import cheetah.distributor.event.ApplicationListener;
import cheetah.distributor.event.Event;
import cheetah.distributor.machine.AbstractMachine;
import cheetah.distributor.machine.Feedback;

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

    @Override
    public void execute(Event event) {
        try {
            ApplicationListener listener = ((ApplicationListener) this.getEventListener());
            listener.onApplicationEvent((ApplicationEvent) event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Feedback completeWork(Event event) {
        try {
            ApplicationListener listener = ((ApplicationListener) this.getEventListener());
            listener.onApplicationEvent((ApplicationEvent) event);
        } catch (Exception e) {
            e.printStackTrace();
            return Feedback.FAILURE;
        }
        return Feedback.SUCCESS;
    }


}
