package cheetah.distributor;

import cheetah.distributor.handler.ApplicationEventHandler;
import cheetah.distributor.handler.Handler;
import cheetah.distributor.handler.HandlerTyped;
import cheetah.event.ApplicationEvent;
import cheetah.event.ApplicationListener;
import cheetah.event.DomainEventListener;
import cheetah.logger.Debug;
import org.junit.Test;

import java.util.concurrent.Executors;

/**
 * Created by Max on 2016/2/2.
 */
public class HandlerTest {
    @Test
    public void log() {
        Debug.log(Distributor.class, "a");
    }

    @Test
    public void handlers() {
        HandlerTyped typed = HandlerTyped.Manager.convertFrom(DomainEventListener.class);
        System.out.println(typed);
    }

    @Test
    public void applicatoinhandler() {
        Handler handler = new ApplicationEventHandler((ApplicationListener<ApplicationEventTest>) event -> {
            System.out.println(event.occurredTime());
            System.out.println(event.getSource());
            throw new RuntimeException();
        }, Executors.newCachedThreadPool());
        handler.handle(new EventMessage(new ApplicationEventTest("test"), true), (exception, eventMessage, exceptionObject, exceptionMessage) -> {
            System.out.println("-----------------------------------------------");
            System.out.println(exception);
            System.out.println(eventMessage);
            System.out.println(exceptionObject);
            System.out.println(exceptionMessage);
        });
    }

    public static class ApplicationEventTest extends ApplicationEvent {

        /**
         * Constructs a prototypical Event.
         *
         * @param source The object on which the Event initially occurred.
         * @throws IllegalArgumentException if source is null.
         */
        public ApplicationEventTest(Object source) {
            super(source);
        }
    }

}
