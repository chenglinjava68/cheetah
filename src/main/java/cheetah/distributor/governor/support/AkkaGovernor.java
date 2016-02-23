package cheetah.distributor.governor.support;

import akka.actor.ActorRef;
import cheetah.distributor.event.Event;
import cheetah.distributor.governor.Command;
import cheetah.distributor.governor.CommandFactory;
import cheetah.distributor.governor.Governor;
import cheetah.distributor.machine.Feedback;
import cheetah.distributor.machine.Machine;
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
    private Boolean needResult;
    private Event event;
    private List<Machine> workers = new ArrayList<>();
    private ActorRef watcher;

    @Override
    public Governor initialize() {
        if (StringUtils.isBlank(id))
            this.id = IDGenerator.generateId();
        if (Objects.isNull(fisrtSucceed))
            this.fisrtSucceed = false;
        return this;
    }

    @Override
    public Governor on() {
        return this;
    }

    @Override
    public void off() {
        watcher.tell(Command.CLOSE, watcher);
    }

    @Override
    public Feedback command() {
        Assert.notNull(event, "event must not be null");
        return notifyAllWorker();
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
    public Governor registerMachine(Machine worker) {
        this.workers.add(worker);
        return this;
    }

    @Override
    public Governor registerMachineSquad(List<Machine> workers) {
        this.workers.addAll(workers);
        return this;
    }

    @Override
    public Governor setNeedResult(boolean needResult) {
        this.needResult = needResult;
        return this;
    }

    @Override
    public void removeWorker(Machine worker) {
        Assert.notNull(worker, "observer must not be null");
        workers.add(worker);
    }

    public void setWorkerWatcher(ActorRef watcher) {
        this.watcher = watcher;
    }

    private Feedback notifyAllWorker() {
        if (workers.isEmpty())
            return Feedback.EMPTY;
        Command command = CommandFactory.getFactory()
                .setEvent(this.event)
                .setFisrtWin(this.fisrtSucceed)
                .setNeedReport(this.needResult)
                .setMachines(workers)
                .build();
        if (!this.needResult)
            this.watcher.tell(command, ActorRef.noSender());
        return new Feedback();
    }
}
