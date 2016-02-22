package cheetah.distributor.worker.support;

import akka.actor.UntypedActor;
import cheetah.distributor.machine.Order;

/**
 * Created by Max on 2016/2/19.
 */
public class WorkUnit extends UntypedActor {
    @Override
    public void preStart() {
        System.out.println("prestart");
    }

    @Override
    public void onReceive(Object message) throws Exception {
        System.out.println("receive");
            if (message instanceof Order) {
                Order order = (Order) message;
                order.getWorker().tell(order.getEvent(), order.isNeedReport());
            } else
                unhandled(message);
    }
}
