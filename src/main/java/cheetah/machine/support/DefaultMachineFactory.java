package cheetah.machine.support;

import cheetah.machine.Machine;
import cheetah.machine.MachineFactory;

/**
 * Created by Max on 2016/2/21.
 */
public class DefaultMachineFactory implements MachineFactory {

    @Override
    public Machine createApplicationEventMachine() {
        return new ApplicationEventMachine();
    }

    @Override
    public Machine createDomainEventMachine() {
        return new DomainEventMachine();
    }

}
