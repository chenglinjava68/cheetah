package cheetah.engine.support;

import cheetah.async.AsynchronousPoolFactory;
import cheetah.engine.Engine;
import cheetah.event.Event;
import cheetah.governor.Governor;
import cheetah.governor.GovernorFactory;
import cheetah.machine.Machine;
import cheetah.machine.MachineFactory;
import cheetah.mapper.Mapper;
import cheetah.worker.Worker;
import cheetah.worker.WorkerFactory;

/**
 * Created by Max on 2016/2/29.
 */
public class DisruptorEngine implements Engine {

    @Override
    public Machine assignApplicationEventMachine() {
        return null;
    }

    @Override
    public Machine assignDomainEventMachine() {
        return null;
    }

    @Override
    public Governor assignGovernor() {
        return null;
    }

    @Override
    public Governor assignGovernor(Event event) {
        return null;
    }

    @Override
    public Worker assignWorker() {
        return null;
    }

    @Override
    public void setWorkerFactory(WorkerFactory machineryFactory) {

    }

    @Override
    public void setMachineFactory(MachineFactory workerFactory) {

    }

    @Override
    public void setGovernorFactory(GovernorFactory governorFactory) {

    }

    @Override
    public void setMapper(Mapper mapper) {

    }

    @Override
    public void setAsynchronousPoolFactory(AsynchronousPoolFactory asynchronousPoolFactory) {

    }

    @Override
    public Mapper getMapper() {
        return null;
    }

    @Override
    public State state() {
        return null;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
