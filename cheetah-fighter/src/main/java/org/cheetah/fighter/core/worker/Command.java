package org.cheetah.fighter.core.worker;

import org.cheetah.fighter.core.event.Event;

import java.io.Serializable;
import java.util.EventListener;

/**
 * Created by Max on 2016/2/22.
 */
public class Command implements Serializable {

    private static final long serialVersionUID = 2193959876727951577L;

    private Event event;
    private boolean needResult;

    Command() {
    }

    public Command(Event event, boolean needResult) {
        this.event = event;
        this.needResult = needResult;
    }

    public Event event() {
        return event;
    }

    public boolean needResult() {
        return needResult;
    }

    public static Command of(Event event, boolean needResult) {
        return new Command(event, needResult);
    }
}
