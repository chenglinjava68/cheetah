package org.cheetah.fighter.event;

import java.io.Serializable;

/**
 * Created by Max on 2016/1/29.
 */
public abstract class Event<T> implements Serializable {
    private static final long serialVersionUID = -7391802226495046190L;
    private Long occurredTime;
    /**
     * The object on which the Event initially occurred.
     */
    protected T source;

    Event() {
    }

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public Event(T source) {
        if (source == null)
            throw new IllegalArgumentException("null source");
        this.occurredTime = System.currentTimeMillis();
        this.source = source;
    }

    public Long occurredTime() {
        return occurredTime;
    }

    /**
     * The object on which the Event initially occurred.
     *
     * @return The object on which the Event initially occurred.
     */
    public T getSource() {
        return source;
    }

    @Override
    public String toString() {
        return "Event{" +
                "occurredTime=" + occurredTime +
                ", source=" + getSource() +
                "} ";
    }
}
