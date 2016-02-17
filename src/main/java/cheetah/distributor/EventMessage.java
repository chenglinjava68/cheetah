package cheetah.distributor;

import cheetah.distributor.handler.Handler;
import cheetah.event.Event;
import cheetah.util.IDGenerator;

/**
 * Created by Max on 2016/2/7.
 */
public class EventMessage {
    private String id;
    private Event event;
    private Handler.ProcessMode mode;

    public EventMessage() {
        this.id = IDGenerator.generateId();
    }

    public EventMessage(Event event, Handler.ProcessMode mode) {
        this.event = event;
        this.mode = mode;
    }

    public String getId() {
        return id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public void setMode(Handler.ProcessMode mode) {
        this.mode = mode;
    }

    public Handler.ProcessMode getMode() {
        return mode;
    }
}
