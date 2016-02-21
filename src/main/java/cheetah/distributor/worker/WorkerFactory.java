package cheetah.distributor.worker;

/**
 * Created by Max on 2016/2/21.
 */
public interface WorkerFactory {
    Worker createApplicationEventWorker();

    Worker createDomainEventWorker();
}
