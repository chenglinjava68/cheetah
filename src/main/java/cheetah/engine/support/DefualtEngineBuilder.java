package cheetah.engine.support;

import cheetah.engine.EngineBuilder;
import cheetah.governor.GovernorFactory;
import cheetah.governor.support.AkkaGovernorFactory;
import cheetah.mapper.Mapper;
import cheetah.mapper.support.MachineMapper;
import cheetah.worker.WorkerFactory;
import cheetah.worker.support.AkkaWorkerFactory;
import cheetah.machine.MachineFactory;
import cheetah.machine.support.AkkaMachineFactory;

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

    @Override
    public Mapper buildMapper() {
        return new MachineMapper();
    }
}
