package cheetah.distributor.worker.support;

import akka.actor.UntypedActor;
import cheetah.distributor.worker.Order;
import cheetah.distributor.worker.Worker;
import cheetah.util.Assert;

/**
 * Created by Max on 2016/2/21.
 */
public class AkkaWorker extends UntypedActor implements Worker {

    @Override
    public void work(Order order) {
        Assert.notNull(order, "order must not be null");
        try {
            order.getMachine().execute(order.getEvent());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Order) {
            Order order = (Order) message;
            work(order);
        } else unhandled(message);
    }
}
