package cheetah.distributor.handler;

import cheetah.event.Event;
import cheetah.util.ObjectUtils;

import java.util.concurrent.CompletableFuture;

/**
 * Created by Max on 2016/2/7.
 */
public class HandlerAdapter implements HandlerConglomeration {
    private Handler adaptee;

    public HandlerAdapter(Handler adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void handle(Event event) {
        adaptee.handle(event);
    }

    @Override
    public CompletableFuture getFuture() {
        if(!ExpectabilityHandler.class.isAssignableFrom(adaptee.getClass()))
            throw new UnsupportedOperationException();
        return ((ExpectabilityHandler) adaptee).getFuture();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HandlerAdapter that = (HandlerAdapter) o;

        return ObjectUtils.nullSafeEquals(this.adaptee, that.adaptee);
    }

    @Override
    public int hashCode() {
        return ObjectUtils.nullSafeHashCode(this.adaptee) * 29;
    }
}
