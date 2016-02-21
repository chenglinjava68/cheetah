package cheetah.distributor.worker.support;

import cheetah.distributor.machinery.Machinery;
import cheetah.distributor.worker.AbstractWorker;
import cheetah.distributor.worker.Order;
import cheetah.distributor.worker.Report;

import java.util.EventListener;

/**
 * Created by Max on 2016/2/1.
 */
public class GenericEventWorker extends AbstractWorker {

    public GenericEventWorker(EventListener eventListener, Machinery machinery) {
        super(eventListener, machinery);
    }

    @Override
    public Report work(Order eventMessage) {
        return null;
    }
}
