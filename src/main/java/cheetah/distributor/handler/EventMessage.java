package cheetah.distributor.handler;

import cheetah.distributor.event.Event;
import cheetah.util.IDGenerator;

/**
 * Created by Max on 2016/2/7.
 */
public class EventMessage {
    private String id;
    private Event event;
    private int processMode;
    private boolean fisrtWin;

    EventMessage() {
        this.id = IDGenerator.generateId();
    }

    public EventMessage(Builder builder) {
        this.id = IDGenerator.generateId();
        this.event = builder.event;
        this.processMode = builder.processMode;
        this.fisrtWin = builder.fisrtWin;
    }

    public EventMessage(Event event, int processMode) {
        this.id = IDGenerator.generateId();
        this.event = event;
        this.processMode = processMode;
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

    public int getProcessMode() {
        return processMode;
    }

    public void setProcessMode(int processMode) {
        this.processMode = processMode;
    }

    public boolean isFisrtWin() {
        return fisrtWin;
    }

    public void setFisrtWin(boolean fisrtWin) {
        this.fisrtWin = fisrtWin;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        Event event;
        int processMode;
        boolean fisrtWin;

        public Builder setEvent(Event event) {
            this.event = event;
            return this;
        }

        public Builder setProcessMode(int processMode) {
            this.processMode = processMode;
            return this;
        }

        public Builder setFisrtWin(boolean fisrtWin) {
            this.fisrtWin = fisrtWin;
            return this;
        }

        public EventMessage build() {
            return new EventMessage(this);
        }
    }
}
