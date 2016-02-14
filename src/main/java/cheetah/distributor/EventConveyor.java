package cheetah.distributor;

import cheetah.event.Event;

/**
 * Created by Max on 2016/2/3.
 */
public class EventConveyor implements Conveyor {
    private Distributor distributor;

    public EventConveyor(Distributor distributor) {
        this.distributor = distributor;
    }

    @Override
    public void transport(Event event) {

    }
}
