package cheetah.async.disruptor;

import cheetah.event.Event;

/**
 * Created by Max on 2016/2/29.
 */
public class DisruptorEvent {
    private Event event;

    public void set(Event event) {
        this.event = event;
    }

    public Event get() {
        return this.event;
    }
}
