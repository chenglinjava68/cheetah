package cheetah.distributor;

import cheetah.event.ApplicationEvent;
import cheetah.event.ApplicationListener;
import cheetah.event.Event;
import org.springframework.util.Assert;

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
    public Handler handle(Event event) {
        Assert.isNull(event);
        CompletableFuture<EventResult> future = CompletableFuture.supplyAsync(() -> {
            ApplicationEvent applicationEvent = (ApplicationEvent) event;
            ApplicationListener<ApplicationEvent> listener = (ApplicationListener<ApplicationEvent>) this.getEventListener();
            listener.onApplicationEvent(applicationEvent);
            return new EventResult<>(applicationEvent.getSource());
        }, getExecutorService());
        this.setFuture(future);
        return this;
    }
}
