package cheetah.distributor;

import cheetah.event.Event;

/**
 * Created by Max on 2016/1/10.
 */
public abstract class EventEmitter {
    private EventEmitter() {}

    public static <E extends Event> void launchedEvent(E event) {

    }


}
