package cheetah.distributor;

import cheetah.distributor.core.Distributor;
import cheetah.distributor.handler.EventMessage;
import cheetah.distributor.handler.EventResult;

/**
 * Created by Max on 2016/2/17.
 */
public class Regulator {
    private Distributor distributor;

    public Regulator(Distributor distributor) {
        this.distributor = distributor;
    }

    public EventResult delivery(EventMessage eventMessage) {
        return distributor.allot(eventMessage);
    }

}
