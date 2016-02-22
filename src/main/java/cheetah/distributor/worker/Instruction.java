package cheetah.distributor.worker;

import cheetah.distributor.event.Event;
import cheetah.util.ObjectUtils;

import java.util.EventListener;

/**
 * Created by Max on 2016/2/19.
 */
public class Instruction {
    public final static Instruction CLOSE = new Instruction(null, null);
    private Event event;
    private EventListener eventListener;

    public Instruction(Event event, EventListener eventListener) {
        this.event = event;
        this.eventListener = eventListener;
    }

    public Event getEvent() {
        return event;
    }

    public EventListener getEventListener() {
        return eventListener;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Instruction that = (Instruction) o;

        return ObjectUtils.nullSafeEquals(this.event, that.event) && ObjectUtils.nullSafeEquals(this.eventListener, that.eventListener);

    }

    @Override
    public int hashCode() {
        return ObjectUtils.nullSafeHashCode(this.event) * 29 + ObjectUtils.nullSafeHashCode(this.eventListener);
    }
}
