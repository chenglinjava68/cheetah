package cheetah.distributor.engine.support;

import akka.actor.ActorSystem;
import cheetah.distributor.core.DispatcherWorker;
import cheetah.distributor.engine.Engine;
import cheetah.distributor.governor.Governor;
import cheetah.distributor.governor.GovernorFactory;
import cheetah.distributor.machinery.Machinery;
import cheetah.distributor.machinery.MachineryFactory;
import cheetah.distributor.machinery.support.AkkaMachineryFactory;
import cheetah.distributor.worker.Worker;
import cheetah.distributor.worker.WorkerFactory;
import cheetah.distributor.worker.support.AkkaWorkerFactory;
import cheetah.logger.Debug;
import cheetah.plugin.InterceptorChain;
import cheetah.util.Assert;
import com.typesafe.config.ConfigFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Max on 2016/2/1.
 */
public class DefaultEngine implements Engine {
    private final Map<WorkerCacheKey, List<Worker>> workers = new HashMap<>();
    final ThreadLocal<Governor> governors = new ThreadLocal<>();
    private MachineryFactory machineryFactory;
    private WorkerFactory workerFactory;
    private GovernorFactory governorFactory;
    private InterceptorChain interceptorChain;
    private ActorSystem actorSystem;
    private STATE state;

    public DefaultEngine() {
        this.state = STATE.NEW;
    }

    @Override
    public void start() {
        if (isRunning()) {
            Debug.log(this.getClass(), "DefaultEngine start ...");
            actorSystem = ActorSystem.create("system", ConfigFactory.load("akka.conf"));
            machineryFactory = new AkkaMachineryFactory();
            workerFactory = new AkkaWorkerFactory();
            state = STATE.RUNNING;
        } else Debug.log(this.getClass(), "DefualtEngine is started.");
    }

    @Override
    public void stop() {
        while (!actorSystem.isTerminated())
            actorSystem.shutdown();
        state = STATE.STOP;
        Debug.log(this.getClass(), "DefualtEngine has been shut down.");
    }

    @Override
    public void registerWorkers(WorkerCacheKey cacheKey, List<Worker> workers) {
        Assert.notNull(cacheKey, "listener must not be null");
        Assert.notNull(workers, "workers must not be null");
        this.workers.put(cacheKey, workers);
    }

    @Override
    public List<Worker> assignWorkers(WorkerCacheKey cacheKey) {
        return this.workers.get(cacheKey);
    }

    @Override
    public Worker assignApplicationEventWorker() {
        return workerFactory.createApplicationEventWorker();
    }

    @Override
    public Worker assignDomainEventWorker() {
        return workerFactory.createApplicationEventWorker();
    }

    @Override
    public Machinery assignMachinery() {
        return machineryFactory.createMachinery();
    }

    @Override
    public Governor assignGovernor() {
        Governor governor = governorFactory.createGovernor();
        governors.set(governor);
        return governor;
    }

    @Override
    public boolean isExists(DispatcherWorker.ListenerCacheKey cacheKey) {
        return this.workers.containsKey(cacheKey);
    }

    @Override
    public void setMachineryFactory(MachineryFactory machineryFactory) {
        this.machineryFactory = machineryFactory;
    }

    @Override
    public void setWorkerFactory(WorkerFactory workerFactory) {
        this.workerFactory = workerFactory;
    }

    public void setInterceptorChain(InterceptorChain interceptorChain) {
        this.interceptorChain = interceptorChain;
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

    private boolean isRunning() {
        if (this.state.equals(STATE.RUNNING))
            return true;
        else return false;
    }

}
