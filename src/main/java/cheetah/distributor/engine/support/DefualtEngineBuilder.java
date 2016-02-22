package cheetah.distributor.engine.support;

import cheetah.distributor.engine.EngineBuilder;
import cheetah.distributor.governor.GovernorFactory;
import cheetah.distributor.governor.support.AkkaGovernorFactory;
import cheetah.distributor.worker.WorkerFactory;
import cheetah.distributor.worker.support.AkkaWorkerFactory;
import cheetah.distributor.machine.MachineFactory;
import cheetah.distributor.machine.support.AkkaMachineFactory;

/**
 * Created by Max on 2016/2/19.
 */
public class DefualtEngineBuilder implements EngineBuilder {

    @Override
    public MachineFactory buildMachineFactory() {
        return new AkkaMachineFactory();
    }

    @Override
    public GovernorFactory buildGovernorFactory() {
        return new AkkaGovernorFactory();
    }

    @Override
    public WorkerFactory buildWorkerFactory() {
        return new AkkaWorkerFactory();
    }
}
