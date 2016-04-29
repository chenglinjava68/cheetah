package org.cheetah.fighter.worker;

import org.cheetah.fighter.event.Callback;
import org.cheetah.fighter.event.Event;

import java.io.Serializable;
import java.util.EventListener;

/**
 * Created by Max on 2016/2/22.
 */
public class Command implements Serializable {

    private static final long serialVersionUID = 2193959876727951577L;

    private Event event;
    private Callback callback;
    private boolean needResult ;
    private Class<? extends EventListener> eventListener;

    Command() {
    }

    public Command(Event event, Callback callback, boolean needResult, Class<? extends EventListener> eventListener) {
        this.event = event;
        this.callback = callback;
        this.needResult = needResult;
        this.eventListener = eventListener;
    }

    public Event event() {
        return event;
    }

    public boolean needResult() {
        return needResult;
    }

    public Callback callback() {
        return callback;
    }

    public Class<? extends EventListener> eventListener() {
        return eventListener;
    }

    public static Command of(Event event, Class<? extends EventListener> eventListener) {
        return new Command(event, null, true, eventListener);
    }


    public static Command of(Event event, Callback callback, Class<? extends EventListener> eventListener) {
        return new Command(event, callback, true, eventListener);
    }
}
