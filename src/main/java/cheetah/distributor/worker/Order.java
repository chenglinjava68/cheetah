package cheetah.distributor.worker;

import cheetah.distributor.event.Event;
import cheetah.util.IDGenerator;

/**
 * Created by Max on 2016/2/7.
 */
public class Order {
    private String id;
    private Event event;
    private boolean fisrtWin;

    public Order(Event event) {
        this.id = IDGenerator.generateId();
        this.event = event;
    }

    public Order(Event event, boolean fisrtWin) {
        this(event);
        this.fisrtWin = fisrtWin;
    }

    public Order(String id, Event event, boolean fisrtWin) {
        this(event, fisrtWin);
        this.id = id;
        this.fisrtWin = fisrtWin;
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

    public boolean isFisrtWin() {
        return fisrtWin;
    }

    public void setFisrtWin(boolean fisrtWin) {
        this.fisrtWin = fisrtWin;
    }

}
