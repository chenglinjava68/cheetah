package cheetah.distributor.engine;

import cheetah.distributor.Startable;
import cheetah.distributor.core.DispatcherWorker;
import cheetah.distributor.governor.Governor;
import cheetah.distributor.governor.GovernorFactory;
import cheetah.distributor.machinery.Machinery;
import cheetah.distributor.machinery.MachineryFactory;
import cheetah.distributor.worker.Worker;
import cheetah.distributor.worker.WorkerFactory;
import cheetah.util.ObjectUtils;

import java.util.List;

/**
 * Created by Max on 2016/2/19.
 */
public interface Engine extends Startable {

    void registerWorkers(WorkerCacheKey cacheKey, List<Worker> workers);

    List<Worker> assignWorkers(WorkerCacheKey cacheKey);

    Worker assignApplicationEventWorker();

    Worker assignDomainEventWorker();

    Governor assignGovernor();

    Machinery assignMachinery();

    STATE getState();

    boolean isExists(DispatcherWorker.ListenerCacheKey cacheKey);

    Governor getCurrentGovernor();

    void removeCurrentGovernor();

    void setMachineryFactory(MachineryFactory machineryFactory);

    void setWorkerFactory(WorkerFactory workerFactory);

    void setGovernorFactory(GovernorFactory governorFactory);

    enum STATE {
        NEW, RUNNING, STOP
    }

    class WorkerCacheKey {
        private DispatcherWorker.ListenerCacheKey listenerCacheKey;

        WorkerCacheKey(DispatcherWorker.ListenerCacheKey listenerCacheKey) {
            this.listenerCacheKey = listenerCacheKey;
        }

        public static WorkerCacheKey generate(DispatcherWorker.ListenerCacheKey listenerCacheKey) {
            return new WorkerCacheKey(listenerCacheKey);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            WorkerCacheKey that = (WorkerCacheKey) o;

            return ObjectUtils.nullSafeEquals(this.listenerCacheKey, that.listenerCacheKey);

        }

        @Override
        public int hashCode() {
            return ObjectUtils.nullSafeHashCode(this.listenerCacheKey) * 29;
        }
    }


}
