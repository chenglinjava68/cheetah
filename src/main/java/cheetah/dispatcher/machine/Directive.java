package cheetah.dispatcher.machine;

import cheetah.dispatcher.event.Event;

/**
 * Created by Max on 2016/2/22.
 */
public class Directive {
    private String name;
    private Machine worker;
    private Event event;
    private boolean needReport;

    public Directive() {
    }

    public Directive(String name, Machine worker, Event event, boolean needReport) {
        this.name = name;
        this.worker = worker;
        this.event = event;
        this.needReport = needReport;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Machine getWorker() {
        return worker;
    }

    public void setWorker(Machine worker) {
        this.worker = worker;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public boolean isNeedReport() {
        return needReport;
    }

    public void setNeedReport(boolean needReport) {
        this.needReport = needReport;
    }
}
