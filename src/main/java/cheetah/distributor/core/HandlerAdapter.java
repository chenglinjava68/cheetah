package cheetah.distributor.core;

import cheetah.distributor.event.Event;
import cheetah.distributor.worker.Worker;
import cheetah.distributor.machine.Report;
import cheetah.distributor.machine.Machine;
import cheetah.plugin.InterceptorChain;

import java.util.EventListener;

/**
 * Created by Max on 2016/2/7.
 */
public class HandlerAdapter implements Machine {
    private Machine adaptee;
    private InterceptorChain interceptorChain;

    public HandlerAdapter(Machine adaptee, InterceptorChain interceptorChain) {
        this.interceptorChain = interceptorChain;
        adapteeExtended(adaptee);
    }

    private void adapteeExtended(Machine $adaptee) {
        this.adaptee = (Machine) this.interceptorChain.pluginAll($adaptee);
    }

    @Override
    public void work(Event event) {
        adaptee.work(event);
    }

    @Override
    public Report completeWork(Event event) {
        return adaptee.completeWork(event);
    }

    @Override
    public void setEventListener(EventListener eventListener) {
        adaptee.setEventListener(eventListener);
    }

    @Override
    public void setWorker(Worker machinery) {
        adaptee.setWorker(machinery);
    }

    @Override
    public Worker getWorker() {
        return adaptee.getWorker();
    }

    @Override
    public Machine kagebunsin() throws CloneNotSupportedException {
        return adaptee.kagebunsin();
    }

    @Override
    public Machine kagebunsin(EventListener listener) throws CloneNotSupportedException {
        return adaptee.kagebunsin(listener);
    }

}
