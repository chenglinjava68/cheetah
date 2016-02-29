package cheetah.engine.support;

import akka.actor.*;
import akka.pattern.Patterns;
import akka.routing.SmallestMailboxPool;
import cheetah.engine.Engine;
import cheetah.governor.Governor;
import cheetah.governor.GovernorFactory;
import cheetah.governor.support.AkkaGovernor;
import cheetah.machine.Machine;
import cheetah.machine.MachineFactory;
import cheetah.machine.support.AkkaMachineFactory;
import cheetah.mapper.Mapper;
import cheetah.worker.Worker;
import cheetah.worker.WorkerFactory;
import cheetah.worker.support.AkkaWorker;
import cheetah.worker.support.AkkaWorkerFactory;
import cheetah.common.logger.Debug;
import cheetah.plugin.InterceptorChain;
import com.typesafe.config.ConfigFactory;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.Collections;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Max on 2016/2/1.
 */
public class DefaultEngine implements Engine {
    public final static String STATUS_CHECK_MSG = "STATUS_CHECK_MSG";
    public final static String STATUS_OK = "OK";
    private WorkerFactory workerFactory;
    private MachineFactory machineFactory;
    private GovernorFactory governorFactory;
    private InterceptorChain interceptorChain;
    private ActorSystem actorSystem;
    private volatile Mapper mapper;
    private ActorRef worker;
    private volatile STATE state;
    private Random random = new Random();
    private AtomicInteger actorNumber = new AtomicInteger(0);

    public DefaultEngine() {
        this.state = STATE.NEW;
    }

    @Override
    public void start() {
        if (!isRunning()) {
            Debug.log(this.getClass(), "DefaultEngine start ...");
            initializeActorSystem();
            ok();
            if (Objects.isNull(workerFactory))
                workerFactory = new AkkaWorkerFactory();
            if (Objects.isNull(machineFactory))
                machineFactory = new AkkaMachineFactory();
            state = STATE.RUNNING;
        } else Debug.log(this.getClass(), "DefualtEngine is started.");
    }

    private void ok() {
        Future<Object> ok = Patterns.ask(worker, STATUS_CHECK_MSG, 5000);
        try {
            Object result = Await.result(ok, Duration.create(5000, TimeUnit.MILLISECONDS));
            if(result.equals(STATUS_OK))
                return;
            else ok();
        } catch (Exception e) {
            ok();
        }
    }

    @Override
    public void stop() {
        while (!actorSystem.isTerminated())
            actorSystem.shutdown();
        workerFactory = null;
        machineFactory = null;
        governorFactory = null;
        interceptorChain = null;
        actorSystem = null;
        state = STATE.STOP;
        Debug.log(this.getClass(), "DefualtEngine has been shut down.");
    }

    private void initializeActorSystem() {
        actorSystem = ActorSystem.create("cheetah_system", ConfigFactory.load("akka.conf"));
        createActor();
    }

    private void createActor() {
        SupervisorStrategy strategy = new OneForOneStrategy(3, Duration.create("1 minute"), Collections.<Class<? extends Throwable>>singletonList(Exception.class));
        worker = actorSystem.actorOf(Props.create(AkkaWorker.class, this.mapper)
                .withRouter(new SmallestMailboxPool(5000).withSupervisorStrategy(strategy)), "worker-master");
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
        ((AkkaGovernor) governor).setWorker(this.worker);
        return governor;
    }

    @Override
    public void setWorkerFactory(WorkerFactory workerFactory) {
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
    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Mapper getMapper() {
        return this.mapper;
    }

    @Override
    public STATE getState() {
        return state;
    }

    public void setInterceptorChain(InterceptorChain interceptorChain) {
        this.interceptorChain = interceptorChain;
    }

}
