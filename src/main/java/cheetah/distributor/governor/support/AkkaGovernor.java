package cheetah.distributor.governor.support;

import cheetah.distributor.worker.Report;
import cheetah.distributor.event.Event;
import cheetah.distributor.governor.Governor;
import cheetah.distributor.worker.Order;
import cheetah.distributor.worker.Worker;
import cheetah.util.Assert;
import cheetah.util.IDGenerator;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Max on 2016/2/20.
 */
public class AkkaGovernor implements Governor {
    private String id;
    private Boolean fisrtSucceed;
    private Event event;
    private List<Worker> workers = new ArrayList<>();

    @Override
    public Governor initialize() {
        if (StringUtils.isBlank(id))
            this.id = IDGenerator.generateId();
        if (Objects.isNull(fisrtSucceed))
            this.fisrtSucceed = false;
        return this;
    }

    @Override
    public void on() {

    }

    @Override
    public void off() {

    }

    @Override
    public Report command() {
        Assert.notNull(event, "event must not be null");
        Order message = new Order(id, event, fisrtSucceed);
        return notifyAllWorker(message);
    }

    @Override
    public Governor setEvent(Event event) {
        Assert.notNull(event, "event must not be null");
        this.event = event;
        return this;
    }

    @Override
    public Event getEven() {
        return this.event;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public Governor setFisrtSucceed(Boolean fisrtSucceed) {
        this.fisrtSucceed = fisrtSucceed;
        return this;
    }

    @Override
    public Governor registerWorker(Worker worker) {
        Assert.notNull(worker, "observer must not be null");
        workers.add(worker);
        return this;
    }

    @Override
    public Governor registerWorker(List<Worker> workers) {
        workers.addAll(workers);
        return this;
    }

    @Override
    public void removeWorker(Worker worker) {
        Assert.notNull(worker, "observer must not be null");
        workers.add(worker);
    }

    private Report notifyAllWorker(Order message) {
        for (Worker worker : workers) {
            worker.tell(message);
        }
        return new Report();
    }
}
