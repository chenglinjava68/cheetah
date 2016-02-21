package cheetah.distributor.core;

import cheetah.distributor.event.Event;

/**
 * Created by Max on 2016/2/17.
 */
public class Regulator {
    private DispatcherWorker distributor;

    public Regulator(DispatcherWorker distributor) {
        this.distributor = distributor;
    }

    public EventResult delivery(Event event) {
        return distributor.receive(event);
    }

}
