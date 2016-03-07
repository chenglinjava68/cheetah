package cheetah.handler;

import cheetah.core.event.Event;

/**
 * 机器指令
 * Created by Max on 2016/2/29.
 */
public class Directive {
    private Event event;
    private boolean feedback;

    public Directive(Event event, boolean feedback) {
        this.event = event;
        this.feedback = feedback;
    }

    public Event event() {
        return event;
    }

    public boolean feedback() {
        return feedback;
    }
}
