package org.cheetah.fighter.core.handler;

import org.cheetah.fighter.core.event.Callback;
import org.cheetah.fighter.core.event.Event;

/**
 * 机器指令
 * Created by Max on 2016/2/29.
 */
public class Directive {
    private Event event;
    private Callback callback;
    private boolean feedback;

    public Directive(Event event, Callback callback, boolean feedback) {
        this.event = event;
        this.callback = callback;
        this.feedback = feedback;
    }

    public Event event() {
        return event;
    }

    public Callback callback() {
        return callback;
    }

    public boolean feedback() {
        return feedback;
    }
}
