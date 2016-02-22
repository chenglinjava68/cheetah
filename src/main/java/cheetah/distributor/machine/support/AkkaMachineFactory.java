package cheetah.distributor.machine.support;

import cheetah.distributor.machine.Machine;
import cheetah.distributor.machine.MachineFactory;

/**
 * Created by Max on 2016/2/21.
 */
public class AkkaMachineFactory implements MachineFactory {

    @Override
    public Machine createApplicationEventMachine() {
        return new ApplicationEventMachine();
    }

    @Override
    public Machine createDomainEventMachine() {
        return new DomainEventMachine();
    }

}
