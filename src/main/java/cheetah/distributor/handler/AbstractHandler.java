package cheetah.distributor.handler;

import cheetah.distributor.EventMessage;
import cheetah.distributor.event.Event;
import cheetah.util.Assert;

import java.util.EventListener;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

/**
 * Created by Max on 2016/2/14.
 */
public abstract class AbstractHandler implements Handler {
    private EventListener eventListener;
    private volatile ExecutorService executorService;
    private static final ThreadLocal<CompletableFuture<Boolean>> futures = new ThreadLocal<>();

    public AbstractHandler(EventListener eventListener, ExecutorService executorService) {
        Assert.notNull(eventListener, "eventListener must not be null");
        Assert.notNull(executorService, "executorService must not be null");
        this.eventListener = eventListener;
        this.executorService = executorService;
    }

    @Override
    public void handle(EventMessage eventMessage) {
        Event event = eventMessage.getEvent();
        Assert.notNull(event);
        CompletableFuture<Boolean> future = statefulHandle(event);
        AbstractHandler.futures.set(future);
    }

    @Override
    public void handle(EventMessage eventMessage, boolean nativeAsync) {
        Event event = eventMessage.getEvent();
        Assert.notNull(event);
        if (nativeAsync)
            statelessNativeAsyncHandle(event);
        else
            statelessHandle(event);
    }

    @Override
    public void handle(EventMessage eventMessage, HandleCallback callback) {
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

    private void handleAssert(EventMessage event, HandleCallback callback) {
        Assert.notNull(event, "eventMessage must not be null");
        Assert.notNull(event.getEvent(), "event must not be null");
        Assert.notNull(callback, "callback must not be null");
    }

    @Override
    public final CompletableFuture<Boolean> getFuture() {
        return futures.get();
    }

    public final EventListener getEventListener() {
        return eventListener;
    }

    public final ExecutorService getExecutorService() {
        return executorService;
    }

    public abstract void statelessHandle(Event event);

    public abstract CompletableFuture<Boolean> statefulHandle(Event event);

    public abstract void statelessNativeAsyncHandle(Event event);

}
