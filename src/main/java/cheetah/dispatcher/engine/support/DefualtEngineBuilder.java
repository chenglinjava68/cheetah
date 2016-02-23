package cheetah.dispatcher.engine.support;

import cheetah.dispatcher.engine.EngineBuilder;
import cheetah.dispatcher.governor.GovernorFactory;
import cheetah.dispatcher.governor.support.AkkaGovernorFactory;
import cheetah.dispatcher.worker.WorkerFactory;
import cheetah.dispatcher.worker.support.AkkaWorkerFactory;
import cheetah.dispatcher.machine.MachineFactory;
import cheetah.dispatcher.machine.support.AkkaMachineFactory;

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
