package cheetah.distributor.governor;

import cheetah.distributor.machine.Report;
import cheetah.distributor.event.Event;
import cheetah.distributor.machine.Machine;

import java.util.List;

/**
 * Created by Max on 2016/2/20.
 */
public interface Governor {
    Governor initialize();

    Governor on();

    void off();

    Report command();

    Governor setEvent(Event event);

    Event getEven();

    String getId();

    Governor setFisrtSucceed(Boolean fisrtSucceed);

    Governor registerMachine(Machine worker);

    Governor registerMachineSquad(List<Machine> workers);

    Governor setNeedResult(boolean needResult);

    void removeWorker(Machine worker);

}
