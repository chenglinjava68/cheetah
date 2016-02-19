package cheetah.distributor.event;

import java.util.EventObject;

/**
 * Created by Max on 2016/1/29.
 */
public abstract class Event extends EventObject {
    private Long occurredTime;
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public Event(Object source) {
        super(source);
        this.occurredTime = System.currentTimeMillis();
    }

    public Long occurredTime() {
        return occurredTime;
    }

    @Override
    public String toString() {
        return "Event{" +
                "occurredTime=" + occurredTime +
                ", source=" + getSource() +
                "} ";
    }
}
