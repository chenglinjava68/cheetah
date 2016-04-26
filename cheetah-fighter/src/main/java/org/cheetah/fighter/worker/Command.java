package org.cheetah.fighter.worker;

import org.cheetah.fighter.event.Event;

import java.io.Serializable;
import java.util.EventListener;

/**
 * Created by Max on 2016/2/22.
 */
public class Command implements Serializable {

    private static final long serialVersionUID = 2193959876727951577L;

    private Event event;
    private boolean needResult;
    private Class<? extends EventListener> eventListener;

    Command() {
    }

    public Command(Event event, boolean needResult, Class<? extends EventListener> eventListener) {
        this.event = event;
        this.needResult = needResult;
        this.eventListener = eventListener;
    }

    public Event event() {
        return event;
    }

    public boolean needResult() {
        return needResult;
    }

    public Class<? extends EventListener> eventListener() {
        return eventListener;
    }

    public static Command of(Event event, Class<? extends EventListener> eventListener) {
        return new Command(event, true, eventListener);
    }


    public static Command of(Event event, boolean needResult, Class<? extends EventListener> eventListener) {
        return new Command(event, needResult, eventListener);
    }
}
