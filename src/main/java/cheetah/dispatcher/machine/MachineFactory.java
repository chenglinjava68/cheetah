package cheetah.dispatcher.machine;

/**
 * Created by Max on 2016/2/21.
 */
public interface MachineFactory {
    Machine createApplicationEventMachine();

    Machine createDomainEventMachine();
}
