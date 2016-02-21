package cheetah.distributor.governor;

import cheetah.distributor.worker.Report;
import cheetah.distributor.event.Event;
import cheetah.distributor.worker.Worker;

import java.util.List;

/**
 * Created by Max on 2016/2/20.
 */
public interface Governor {
    Governor initialize();

    void on();

    void off();

    Report command();

    Governor setEvent(Event event);

    Event getEven();

    String getId();

    Governor setFisrtSucceed(Boolean fisrtSucceed);

    Governor registerWorker(Worker worker);

    Governor registerWorker(List<Worker> workers);

    void removeWorker(Worker worker);

}
