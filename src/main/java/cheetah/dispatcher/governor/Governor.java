package cheetah.dispatcher.governor;

import cheetah.dispatcher.machine.Feedback;
import cheetah.dispatcher.event.Event;
import cheetah.dispatcher.machine.Machine;

import java.util.List;

/**
 * Created by Max on 2016/2/20.
 */
public interface Governor {
    Governor initialize();

    Governor on();

    void off();

    Feedback command();

    Governor setEvent(Event event);

    Event getEven();

    String getId();

    Governor setFisrtSucceed(Boolean fisrtSucceed);

    Governor registerMachine(Machine worker);

    Governor registerMachineSquad(List<Machine> workers);

    Governor setNeedResult(boolean needResult);

    void removeWorker(Machine worker);

}
