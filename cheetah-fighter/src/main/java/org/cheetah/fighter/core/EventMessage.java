package org.cheetah.fighter.core;

import org.cheetah.fighter.event.Event;

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

    public void setEvent(Event event) {
        this.event = event;
    }

    public void setNeedResult(boolean needResult) {
        this.needResult = needResult;
    }

    public void setFisrtWin(boolean fisrtWin) {
        this.fisrtWin = fisrtWin;
    }

    public Event event() {
        return event;
    }

    public boolean needResult() {
        return needResult;
    }

    public boolean fisrtWin() {
        return fisrtWin;
    }


}
