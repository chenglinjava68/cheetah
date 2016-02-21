package cheetah.distributor.worker;

import cheetah.distributor.machinery.Machinery;
import cheetah.util.Assert;
import cheetah.util.ObjectUtils;

import java.util.EventListener;

/**
 * Created by Max on 2016/2/14.
 */
public abstract class AbstractWorker implements Worker {
    private EventListener eventListener;
    private Machinery machinery;

    public AbstractWorker() {
    }

    public AbstractWorker(EventListener eventListener, Machinery machinery) {
        this.eventListener = eventListener;
        this.machinery = machinery;
    }

    @Override
    public Worker kagebunsin() throws CloneNotSupportedException{
        return (Worker) super.clone();
    }

    @Override
    public Worker kagebunsin(EventListener listener) throws CloneNotSupportedException {
        Worker handler = (Worker) super.clone();
        handler.setEventListener(this.eventListener);
        return handler;
    }

    @Override
    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    @Override
    public void setMachinery(Machinery machinery) {
        this.machinery = machinery;
    }

    private void handleAssert(Order event, HandleCallback callback) {
        Assert.notNull(event, "eventMessage must not be null");
        Assert.notNull(event.getEvent(), "event must not be null");
        Assert.notNull(callback, "callback must not be null");
    }

    public EventListener getEventListener() {
        return eventListener;
    }

    public Machinery getMachinery() {
        return machinery;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractWorker that = (AbstractWorker) o;

        return ObjectUtils.nullSafeEquals(this.eventListener, that.eventListener) &&
                ObjectUtils.nullSafeEquals(this.machinery, that.machinery);

    }

    @Override
    public int hashCode() {
        return ObjectUtils.nullSafeHashCode(this.eventListener) * 29 + ObjectUtils.nullSafeHashCode(this.machinery);
    }


    /*

                        @Override
                        public void startup(EventMessage eventMessage, boolean nativeAsync) {
                            Event event = eventMessage.getEvent();
                            Assert.notNull(event);
                            if (nativeAsync)
                                statelessNativeAsyncHandle(event);
                            else
                                statelessHandle(event);
                        }

                        @Override
                        public void startup(EventMessage eventMessage, HandleCallback callback) {
                            handleAssert(eventMessage, callback);
                            CompletableFuture<Boolean> future = statefulHandle(eventMessage.getEvent());
                            try {
                                future.get();
                                callback.doInHandler(Boolean.FALSE, eventMessage, null, null);
                            } catch (InterruptedException | ExecutionException e) {
                                callback.doInHandler(Boolean.TRUE, eventMessage, e.getClass(), e.getMessage());
                            }
                        }


                        @Override
                        public void removeFuture() {
                            AbstractHandler.futures.remove();
                        }


                        @Override
                        public final CompletableFuture<Boolean> getFuture() {
                            return futures.get();
                        }
                        */
}
