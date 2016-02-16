package cheetah.distributor.handler;

import cheetah.distributor.EventMessage;
import cheetah.util.ObjectUtils;

import java.util.concurrent.CompletableFuture;

/**
 * Created by Max on 2016/2/7.
 */
public class HandlerAdapter implements Handler {
    private Handler adaptee;

    public HandlerAdapter(Handler adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void handle(EventMessage event, HandleExceptionCallback callback) {
        adaptee.handle(event, callback);
    }

    @Override
    public void handle(EventMessage eventMessage) {
        adaptee.handle(eventMessage);
    }

    @Override
    public void handle(EventMessage eventMessage, boolean nativeAsync) {
        adaptee.handle(eventMessage, nativeAsync);
    }

    @Override
    public CompletableFuture<Boolean> getFuture() {
        return adaptee.getFuture();
    }

    @Override
    public void removeFuture() {
        adaptee.removeFuture();
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
