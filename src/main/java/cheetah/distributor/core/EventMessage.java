package cheetah.distributor.core;

import cheetah.distributor.event.Event;

/**
 * Created by Max on 2016/2/21.
 */
public class EventMessage {
    private Event event;
    private boolean needResult;
    private boolean fisrtWin;

    public EventMessage(Event event) {
        this.event = event;
        this.needResult = false;
        this.fisrtWin = false;
    }

    public EventMessage(boolean needResult, Event event) {
        this.event = event;
        this.needResult = needResult;
        this.fisrtWin = false;
    }

    public EventMessage(Event event, boolean fisrtWin) {
        this.event = event;
        this.fisrtWin = fisrtWin;
        this.needResult = false;
    }

    public EventMessage(Event event, boolean needResult, boolean fisrtWin) {
        this.event = event;
        this.needResult = needResult;
        this.fisrtWin = fisrtWin;
    }

    public Event getEvent() {
        return event;
    }

    public boolean isNeedResult() {
        return needResult;
    }

    public boolean isFisrtWin() {
        return fisrtWin;
    }
}
