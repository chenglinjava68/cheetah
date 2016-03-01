package cheetah.engine.support;

import akka.actor.ActorRef;
import cheetah.async.AsynchronousPoolFactory;
import cheetah.common.logger.Debug;
import cheetah.engine.Engine;
import cheetah.event.Event;
import cheetah.governor.Governor;
import cheetah.governor.GovernorFactory;
import cheetah.governor.support.AkkaGovernor;
import cheetah.machine.Machine;
import cheetah.machine.MachineFactory;
import cheetah.machine.support.DefaultMachineFactory;
import cheetah.mapper.Mapper;
import cheetah.plugin.InterceptorChain;
import cheetah.worker.Worker;
import cheetah.worker.WorkerFactory;
import cheetah.worker.support.AkkaWorkerFactory;

import java.util.Objects;

/**
 * Created by Max on 2016/2/1.
 */
public class DefaultEngine implements Engine {

    private WorkerFactory workerFactory;
    private MachineFactory machineFactory;
    private GovernorFactory governorFactory;
    private InterceptorChain interceptorChain;
    private AsynchronousPoolFactory<ActorRef> asynchronousPoolFactory;
    private volatile Mapper mapper;
    private volatile Governor governor;
    private State state;

    public DefaultEngine() {
        this.state = State.NEW;
    }

    @Override
    public void start() {
        Debug.log(this.getClass(), "DefaultEngine start ...");
        asynchronousPoolFactory.setMapper(this.mapper);
        initializeActorSystem();
        if (Objects.isNull(workerFactory))
            workerFactory = new AkkaWorkerFactory();
        if (Objects.isNull(machineFactory))
            machineFactory = new DefaultMachineFactory();
        this.state = State.RUNNING;
    }

    @Override
    public void stop() {
        asynchronousPoolFactory.stop();
        workerFactory = null;
        machineFactory = null;
        governorFactory = null;
        interceptorChain = null;
        asynchronousPoolFactory = null;
        Debug.log(this.getClass(), "DefualtEngine has been shut down.");
        this.state = State.STOP;
    }

    private void initializeActorSystem() {
        asynchronousPoolFactory.start();
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
        if(Objects.isNull(this.governor)) {
            governor = governorFactory.createGovernor();
            return governor;
        } else {
            try {
                Governor clone = governor.kagebunsin();
                clone.reset();
                return clone;
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    public Governor assignGovernor(Event event) {
        if(Objects.isNull(this.governor)) {
            governor = governorFactory.createGovernor();
            ((AkkaGovernor) governor).setWorker(asynchronousPoolFactory.getAsynchronous(event));
            return governor;
        } else {
            try {
                Governor clone = governor.kagebunsin();
                clone.reset();
                ActorRef actor = asynchronousPoolFactory.getAsynchronous(event);
                ((AkkaGovernor) clone).setWorker(actor);
                return clone;
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
                governor = governorFactory.createGovernor();
                ((AkkaGovernor) governor).setWorker(asynchronousPoolFactory.getAsynchronous(event));
                return governor;
            }
        }
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
    public void setAsynchronousPoolFactory(AsynchronousPoolFactory asynchronousPoolFactory) {
        this.asynchronousPoolFactory = asynchronousPoolFactory;
    }

    @Override
    public Mapper getMapper() {
        return this.mapper;
    }

    @Override
    public State state() {
        return state;
    }

    public void setInterceptorChain(InterceptorChain interceptorChain) {
        this.interceptorChain = interceptorChain;
    }

}
