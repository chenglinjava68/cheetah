package cheetah.distributor.machinery;

import cheetah.distributor.event.Event;

import java.util.EventListener;
import java.util.List;

/**
 * Created by Max on 2016/2/19.
 */
public class Instruction {
    private Event event;
    private List<? extends EventListener> eventListeners;

    public Instruction(Event event, List<? extends EventListener> eventListeners) {
        this.event = event;
        this.eventListeners = eventListeners;
    }

    public Event getEvent() {
        return event;
    }

    public List<? extends EventListener> getEventListeners() {
        return eventListeners;
    }
}
