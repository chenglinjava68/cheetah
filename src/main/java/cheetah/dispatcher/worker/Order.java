package cheetah.dispatcher.worker;

import cheetah.dispatcher.event.Event;
import cheetah.dispatcher.machine.Machine;
import cheetah.util.ObjectUtils;

/**
 * Created by Max on 2016/2/22.
 */
public class Order {
    public final static Order CLOSE = new Order(null, null, false);
    private Machine machine;
    private Event event;
    private boolean needResult;

    public Order(Machine machine, Event event, boolean needResult) {
        this.event = event;
        this.machine = machine;
        this.needResult = needResult;
    }

    public Event getEvent() {
        return event;
    }

    public Machine getMachine() {
        return machine;
    }

    public boolean isNeedResult() {
        return needResult;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order that = (Order) o;

        return ObjectUtils.nullSafeEquals(this.event, that.event) &&
                ObjectUtils.nullSafeEquals(this.machine, that.machine) &&
                ObjectUtils.nullSafeEquals(this.needResult, that.needResult);

    }

    @Override
    public int hashCode() {
        return ObjectUtils.nullSafeHashCode(this.event) * 29 + ObjectUtils.nullSafeHashCode(this.machine)
                + ObjectUtils.nullSafeHashCode(this.needResult);
    }
}
