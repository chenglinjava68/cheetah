package cheetah.distributor.core;

import cheetah.distributor.machinery.Machinery;
import cheetah.distributor.worker.Order;
import cheetah.distributor.worker.Report;
import cheetah.distributor.worker.Worker;
import cheetah.plugin.InterceptorChain;

import java.util.EventListener;

/**
 * Created by Max on 2016/2/7.
 */
public class HandlerAdapter implements Worker {
    private Worker adaptee;
    private InterceptorChain interceptorChain;

    public HandlerAdapter(Worker adaptee, InterceptorChain interceptorChain) {
        this.interceptorChain = interceptorChain;
        adapteeExtended(adaptee);
    }

    private void adapteeExtended(Worker $adaptee) {
        this.adaptee = (Worker) this.interceptorChain.pluginAll($adaptee);
    }

    @Override
    public Report work(Order eventMessage) {
        return adaptee.work(eventMessage);
    }

    @Override
    public void setEventListener(EventListener eventListener) {
        adaptee.setEventListener(eventListener);
    }

    @Override
    public void setMachinery(Machinery machinery) {
        adaptee.setMachinery(machinery);
    }

    @Override
    public Machinery getMachinery() {
        return adaptee.getMachinery();
    }

    @Override
    public Worker kagebunsin() throws CloneNotSupportedException {
        return adaptee.kagebunsin();
    }

    @Override
    public Worker kagebunsin(EventListener listener) throws CloneNotSupportedException {
        return adaptee.kagebunsin(listener);
    }

}
