package cheetah.distributor;

import cheetah.event.Event;
import cheetah.util.IDGenerator;

/**
 * Created by Max on 2016/2/7.
 */
public class EventMessage {
    private String id;
    private Event event;
    private boolean needResult;

    public EventMessage() {
        this.id = IDGenerator.generateId();
    }

    public EventMessage(Event event, boolean needResult) {
        this.id = IDGenerator.generateId();
        this.event = event;
        this.needResult = needResult;
    }

    public String getId() {
        return id;
    }

    public Event getEvent() {
        return event;
    }

    public boolean isNeedResult() {
        return needResult;
    }

}
