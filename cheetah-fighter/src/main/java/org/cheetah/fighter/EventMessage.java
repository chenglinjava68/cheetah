package org.cheetah.fighter;

/**
 * Created by Max on 2016/2/21.
 */
public class EventMessage {
    private DomainEvent event;
    private boolean needResult;
    private boolean fisrtWin;

    public EventMessage(DomainEvent event) {
        this.event = event;
        this.needResult = false;
        this.fisrtWin = false;
    }

    public EventMessage(boolean needResult, DomainEvent event) {
        this.event = event;
        this.needResult = needResult;
        this.fisrtWin = false;
    }

    public EventMessage(DomainEvent event, boolean fisrtWin) {
        this.event = event;
        this.fisrtWin = fisrtWin;
        this.needResult = false;
    }

    public EventMessage(DomainEvent event, boolean needResult, boolean fisrtWin) {
        this.event = event;
        this.needResult = needResult;
        this.fisrtWin = fisrtWin;
    }

    public void setEvent(DomainEvent event) {
        this.event = event;
    }

    public void setNeedResult(boolean needResult) {
        this.needResult = needResult;
    }

    public void setFisrtWin(boolean fisrtWin) {
        this.fisrtWin = fisrtWin;
    }

    public DomainEvent event() {
        return event;
    }

    public boolean needResult() {
        return needResult;
    }

    public boolean fisrtWin() {
        return fisrtWin;
    }

    @Override
    public String toString() {
        return "EventMessage{" +
                "event=" + event +
                ", needResult=" + needResult +
                ", fisrtWin=" + fisrtWin +
                '}';
    }
}
