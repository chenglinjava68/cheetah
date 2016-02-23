package cheetah.dispatcher.engine;

import cheetah.dispatcher.governor.GovernorFactory;
import cheetah.dispatcher.worker.WorkerFactory;
import cheetah.dispatcher.machine.MachineFactory;

/**
 * Created by Max on 2016/2/19.
 */
public interface EngineBuilder {
    MachineFactory buildMachineFactory();

    GovernorFactory buildGovernorFactory();

    WorkerFactory buildWorkerFactory();
}
