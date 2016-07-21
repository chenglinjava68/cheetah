package org.cheetah.fighter.worker;

import org.cheetah.fighter.DomainEvent;
import org.cheetah.fighter.Event;

import java.io.Serializable;

/**
 * Created by Max on 2016/2/22.
 */
public final class Command implements Serializable {

    private static final long serialVersionUID = 2193959876727951577L;

    private final DomainEvent event;
    private final boolean needResult;

    public Command(DomainEvent event, boolean needResult) {
        this.event = event;
        this.needResult = needResult;
    }

    public final Event event() {
        return event;
    }

    public final boolean needResult() {
        return needResult;
    }

    public static Command of(DomainEvent event, boolean needResult) {
        return new Command(event, needResult);
    }
}
