package cheetah.distributor.machine;

import cheetah.distributor.worker.Worker;
import cheetah.util.ObjectUtils;

import java.util.EventListener;

/**
 * Created by Max on 2016/2/14.
 */
public abstract class AbstractMachine implements Machine {
    private EventListener eventListener;
    private Worker worker;

    public AbstractMachine() {
    }

    public AbstractMachine(EventListener eventListener, Worker worker) {
        this.eventListener = eventListener;
        this.worker = worker;
    }

    @Override
    public Machine kagebunsin() throws CloneNotSupportedException{
        return (Machine) super.clone();
    }

    @Override
    public Machine kagebunsin(EventListener listener) throws CloneNotSupportedException {
        Machine handler = (Machine) super.clone();
        handler.setEventListener(this.eventListener);
        return handler;
    }

    @Override
    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    protected EventListener getEventListener() {
        return eventListener;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractMachine that = (AbstractMachine) o;

        return ObjectUtils.nullSafeEquals(this.eventListener, that.eventListener) &&
                ObjectUtils.nullSafeEquals(this.worker, that.worker);

    }

    @Override
    public int hashCode() {
        return ObjectUtils.nullSafeHashCode(this.eventListener) * 29 + ObjectUtils.nullSafeHashCode(this.worker);
    }

}
