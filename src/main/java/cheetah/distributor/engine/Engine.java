package cheetah.distributor.engine;

import cheetah.distributor.Startable;
import cheetah.distributor.core.DispatcherMachine;
import cheetah.distributor.governor.Governor;
import cheetah.distributor.governor.GovernorFactory;
import cheetah.distributor.worker.Worker;
import cheetah.distributor.worker.WorkerFactory;
import cheetah.distributor.machine.Machine;
import cheetah.distributor.machine.MachineFactory;
import cheetah.util.ObjectUtils;

import java.util.List;

/**
 * Created by Max on 2016/2/19.
 */
public interface Engine extends Startable {

    void registerMachineSquad(MachineCacheKey cacheKey, List<Machine> machines);

    List<Machine> assignMachineSquad(MachineCacheKey cacheKey);

    Machine assignApplicationEventMachine();

    Machine assignDomainEventMachine();

    Governor assignGovernor();

    Worker assignWorker();

    STATE getState();

    boolean isExists(DispatcherMachine.ListenerCacheKey cacheKey);

    void setWorkerFactory(WorkerFactory machineryFactory);

    void setMachineFactory(MachineFactory workerFactory);

    void setGovernorFactory(GovernorFactory governorFactory);

    enum STATE {
        NEW, RUNNING, STOP
    }

    class MachineCacheKey {
        private DispatcherMachine.ListenerCacheKey listenerCacheKey;

        MachineCacheKey(DispatcherMachine.ListenerCacheKey listenerCacheKey) {
            this.listenerCacheKey = listenerCacheKey;
        }

        public static MachineCacheKey generate(DispatcherMachine.ListenerCacheKey listenerCacheKey) {
            return new MachineCacheKey(listenerCacheKey);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            MachineCacheKey that = (MachineCacheKey) o;

            return ObjectUtils.nullSafeEquals(this.listenerCacheKey, that.listenerCacheKey);

        }

        @Override
        public int hashCode() {
            return ObjectUtils.nullSafeHashCode(this.listenerCacheKey) * 29;
        }
    }


}
