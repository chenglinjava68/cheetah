package cheetah.distributor;

import cheetah.event.Event;

/**
 * Created by Max on 2016/2/3.
 */
public class EventCollector extends AbstractCollector {

    public EventCollector(Conveyor conveyor) {
        super(conveyor);
    }

    @Override
    public void collect(Event event) {
        getConveyor().transport(event);
    }


}
