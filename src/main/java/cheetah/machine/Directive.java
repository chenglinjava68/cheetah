package cheetah.machine;

import cheetah.event.Event;

/**
 * 机器指令
 * Created by Max on 2016/2/29.
 */
public class Directive {
    private Event event;
    private boolean needResult;

    public Directive(Event event, boolean needResult) {
        this.event = event;
        this.needResult = needResult;
    }

    public Event event() {
        return event;
    }

    public boolean needResult() {
        return needResult;
    }
}
