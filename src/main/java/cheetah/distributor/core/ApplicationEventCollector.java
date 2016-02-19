package cheetah.distributor.core;

import cheetah.distributor.handler.EventMessage;
import cheetah.distributor.Regulator;
import cheetah.distributor.event.AbstractCollector;
import cheetah.distributor.event.Collector;
import cheetah.distributor.event.Event;

/**
 * Created by Max on 2016/2/3.
 */
public class ApplicationEventCollector extends AbstractCollector {

    public ApplicationEventCollector(Regulator regulator) {
        super(regulator);
    }

    @Override
    public void collect(Event event) {
        getRegulator().delivery(new EventMessage(event, Collector.STATE_CALL_BACK));
    }

    @Override
    public void collect(Event event, int mode) {
        getRegulator().delivery(new EventMessage(event, mode));
    }

    @Override
    public void collect(Event event, int mode, boolean fisrtWin) {
        EventMessage message = EventMessage.newBuilder()
                .setEvent(event)
                .setFisrtWin(fisrtWin)
                .setProcessMode(mode)
                .build();
        getRegulator().delivery(message);
    }

}
