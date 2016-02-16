package cheetah.distributor.handler;

import cheetah.distributor.EventMessage;
import cheetah.event.Event;
import cheetah.util.Assert;

import java.util.EventListener;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

/**
 * Created by Max on 2016/2/14.
 */
public abstract class AbstractHandler implements Handler {
    private final EventListener eventListener;
    private final ExecutorService executorService;
    private final ThreadLocal<CompletableFuture<Boolean>> futures = new ThreadLocal<>();

    public AbstractHandler(EventListener eventListener, ExecutorService executorService) {
        Assert.notNull(eventListener, "eventListener must not be null");
        Assert.notNull(executorService, "executorService must not be null");
        this.eventListener = eventListener;
        this.executorService = executorService;
    }

    @Override
    public void handle(EventMessage eventMessage, HandleCallback callback) {
        handleAssert(eventMessage, callback);
        CompletableFuture<Boolean> future = statelessHandle(eventMessage.getEvent());
        try {
            future.get();
        } catch (InterruptedException e) {
            callback.doInHandler(Boolean.TRUE, eventMessage, e.getClass(), e.getMessage());
        } catch (ExecutionException e) {
            callback.doInHandler(Boolean.TRUE, eventMessage, e.getClass(), e.getMessage());
        }
    }

    @Override
    public void handle(Event event, boolean state) {
        Assert.notNull(event);
        if (state) {
            CompletableFuture<Boolean> future = statelessHandle(event);
            this.futures.set(future);
        } else statefulHandle(event);
    }

    @Override
    public void removeFuture() {
        this.futures.remove();
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

    protected abstract CompletableFuture<Boolean> statelessHandle(Event event);

    protected abstract CompletableFuture<Boolean> statefulHandle(Event event);

}
