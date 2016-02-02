package cheetah.distributor;

import cheetah.event.DomainEvent;

/**
 * Created by Max on 2016/1/10.
 */
public final class Emitter {
    private Emitter() {}

    public static <E extends DomainEvent> void launchedEvent(E event) {

    }


}
