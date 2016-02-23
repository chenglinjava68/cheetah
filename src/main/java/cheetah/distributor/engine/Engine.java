package cheetah.distributor.engine;

import cheetah.distributor.Startable;
import cheetah.distributor.governor.Governor;
import cheetah.distributor.governor.GovernorFactory;
import cheetah.distributor.machine.Machine;
import cheetah.distributor.machine.MachineFactory;
import cheetah.distributor.worker.Worker;
import cheetah.distributor.worker.WorkerFactory;

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
