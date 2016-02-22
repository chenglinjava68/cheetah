package cheetah.distributor.engine.support;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import cheetah.distributor.core.DispatcherWorker;
import cheetah.distributor.engine.Engine;
import cheetah.distributor.governor.Governor;
import cheetah.distributor.governor.GovernorFactory;
import cheetah.distributor.governor.support.AkkaGovernor;
import cheetah.distributor.governor.support.WorkerWatcher;
import cheetah.distributor.worker.Worker;
import cheetah.distributor.worker.WorkerFactory;
import cheetah.distributor.worker.support.AkkaWorkerFactory;
import cheetah.distributor.machine.Machine;
import cheetah.distributor.machine.MachineFactory;
import cheetah.distributor.machine.support.AkkaMachineFactory;
import cheetah.logger.Debug;
import cheetah.plugin.InterceptorChain;
import cheetah.util.Assert;
import com.typesafe.config.ConfigFactory;

import java.util.*;

/**
 * Created by Max on 2016/2/1.
 */
public class DefaultEngine implements Engine {
    private final Map<MachineCacheKey, List<Machine>> machineCache = new HashMap<>();
    private final ThreadLocal<Governor> governors = new ThreadLocal<>();
    private WorkerFactory workerFactory;
    private MachineFactory machineFactory;
    private GovernorFactory governorFactory;
    private InterceptorChain interceptorChain;
    private volatile ActorSystem actorSystem;
    private ActorRef workerGovernor;
    private String actorGovernorName = WorkerWatcher.class.getName();
    private volatile STATE state;

    public DefaultEngine() {
        this.state = STATE.NEW;
    }

    @Override
    public void start() {
        if (!isRunning()) {
            Debug.log(this.getClass(), "DefaultEngine start ...");
            actorSystem = ActorSystem.create("system", ConfigFactory.load("akka.conf"));
            workerGovernor = actorSystem.actorOf(Props.create(WorkerWatcher.class), actorGovernorName);
            if (Objects.isNull(workerFactory))
                workerFactory = new AkkaWorkerFactory();
            if (Objects.isNull(machineFactory))
                machineFactory = new AkkaMachineFactory();
            state = STATE.RUNNING;
        } else Debug.log(this.getClass(), "DefualtEngine is started.");
    }

    @Override
    public void stop() {
        while (!actorSystem.isTerminated())
            actorSystem.shutdown();
        machineCache.clear();
        governors.remove();
        workerFactory = null;
        machineFactory = null;
        governorFactory = null;
        interceptorChain = null;
        actorSystem = null;
        state = STATE.STOP;
        Debug.log(this.getClass(), "DefualtEngine has been shut down.");
    }

    @Override
    public void registerMachine(MachineCacheKey cacheKey, List<Machine> machines) {
        Assert.notNull(cacheKey, "listener must not be null");
        Assert.notNull(machines, "machines must not be null");
        this.machineCache.put(cacheKey, machines);
    }

    @Override
    public List<Machine> assignMachineSquad(MachineCacheKey cacheKey) {
        List<Machine> result = this.machineCache.get(cacheKey);
        return result == null ? Collections.<Machine>emptyList() : Collections.unmodifiableList(result);
    }

    @Override
    public Machine assignApplicationEventMachine() {
        return machineFactory.createApplicationEventMachine();
    }

    @Override
    public Machine assignDomainEventMachine() {
        return machineFactory.createDomainEventMachine();
    }

    @Override
    public Worker assignWorker() {
        return workerFactory.createWorker();
    }

    @Override
    public Governor assignGovernor() {
        Governor governor = governorFactory.createGovernor();
        governors.set(governor);
        ((AkkaGovernor) governor).setWatcher(this.workerGovernor);
        return governor;
    }

    @Override
    public boolean isExists(DispatcherWorker.ListenerCacheKey cacheKey) {
        return this.machineCache.containsKey(cacheKey);
    }

    @Override
    public void setMachineFactory(WorkerFactory workerFactory) {
        this.workerFactory = workerFactory;
    }

    @Override
    public void setMachineFactory(MachineFactory machineFactory) {
        this.machineFactory = machineFactory;
    }

    @Override
    public void setGovernorFactory(GovernorFactory governorFactory) {
        this.governorFactory = governorFactory;
    }

    @Override
    public Governor getCurrentGovernor() {
        return governors.get();
    }

    @Override
    public void removeCurrentGovernor() {
        governors.remove();
    }

    @Override
    public STATE getState() {
        return state;
    }

    public void setInterceptorChain(InterceptorChain interceptorChain) {
        this.interceptorChain = interceptorChain;
    }

    private boolean isRunning() {
        if (this.state.equals(STATE.RUNNING))
            return true;
        else return false;
    }

}
