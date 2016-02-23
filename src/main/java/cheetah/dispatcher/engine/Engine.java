package cheetah.dispatcher.engine;

import cheetah.dispatcher.Startable;
import cheetah.dispatcher.governor.Governor;
import cheetah.dispatcher.governor.GovernorFactory;
import cheetah.dispatcher.machine.Machine;
import cheetah.dispatcher.machine.MachineFactory;
import cheetah.dispatcher.worker.Worker;
import cheetah.dispatcher.worker.WorkerFactory;

/**
 * Created by Max on 2016/2/19.
 */
public interface Engine extends Startable {

    Machine assignApplicationEventMachine();

    Machine assignDomainEventMachine();

    Governor assignGovernor();

    Worker assignWorker();

    STATE getState();

    void setWorkerFactory(WorkerFactory machineryFactory);

    void setMachineFactory(MachineFactory workerFactory);

    void setGovernorFactory(GovernorFactory governorFactory);

    enum STATE {
        NEW, RUNNING, STOP
    }

}
