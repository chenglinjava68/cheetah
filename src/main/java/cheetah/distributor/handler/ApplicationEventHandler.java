package cheetah.distributor.handler;

import cheetah.event.ApplicationEvent;
import cheetah.event.ApplicationListener;
import cheetah.event.Event;

import java.util.EventListener;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

/**
 * Created by Max on 2016/2/1.
 */
public class ApplicationEventHandler extends AbstractHandler {

    public ApplicationEventHandler(EventListener eventListener, ExecutorService executorService) {
        super(eventListener, executorService);
    }

    @Override
    protected CompletableFuture<Boolean> statefulHandle(Event event) {
        return CompletableFuture.supplyAsync(() -> {
            ApplicationEvent applicationEvent = (ApplicationEvent) event;
            ApplicationListener<ApplicationEvent> listener = (ApplicationListener<ApplicationEvent>) this.getEventListener();
            listener.onApplicationEvent(applicationEvent);
            return Boolean.TRUE;
        }, getExecutorService());
    }

    @Override
    protected CompletableFuture<Boolean> statelessHandle(Event event) {
        return null;
    }

}
