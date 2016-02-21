package cheetah.distributor.worker.support;

import cheetah.distributor.worker.AbstractWorker;
import cheetah.distributor.worker.Order;
import cheetah.distributor.worker.Report;

/**
 * Created by Max on 2016/2/1.
 */
public class ApplicationEventWorker extends AbstractWorker {

    @Override
    public Report work(Order eventMessage){
//        return CompletableFuture.supplyAsync(() -> {
//            DomainEventListener<DomainEvent> listener = (DomainEventListener<DomainEvent>) this.getEventListener();
//            DomainEvent domainEvent = (DomainEvent) event;
//            listener.onDomainEvent(domainEvent);
//            return Boolean.TRUE;
//        }, getExecutorService());
        return null;
    }


}
