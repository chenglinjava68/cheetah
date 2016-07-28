package org.cheetah.fighter.api;

import com.google.common.collect.Lists;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.dsl.Disruptor;
import org.cheetah.fighter.async.disruptor.DisruptorEvent;
import org.cheetah.fighter.async.disruptor.DisruptorFactory;
import org.cheetah.fighter.handler.Handler;
import org.cheetah.fighter.handler.support.DomainEventHandlerFactory;
import org.cheetah.fighter.worker.Command;
import org.cheetah.fighter.worker.support.DisruptorWorkerFactory;
import org.junit.Test;

import java.util.Collections;

/**
 * Created by Max on 2016/7/21.
 */
public class DisruptorTest {
    @Test
    public void test() {
        DisruptorWorkerFactory disruptorWorkerFactory = new DisruptorWorkerFactory();
        DisruptorFactory disruptorFactory = new DisruptorFactory();
        disruptorFactory.setRingbufferSize(1024);
        disruptorFactory.setWorkerFactory(disruptorWorkerFactory);
        disruptorFactory.start();
        Handler handler = DomainEventHandlerFactory.getDomainEventHandlerFactory().createDomainEventHandler(new SmartDomainListenerTest2());
        Disruptor<DisruptorEvent> disruptor = disruptorFactory.createAsynchronous("SINGLE", Lists.newArrayList(handler), Collections.emptyList());

        while (true) {
            Command command = Command.of(new EventPublisherTest.DomainEventTest2(new EventPublisherTest.User("name")), false);
            disruptor.publishEvent(new Translator(), command);
        }

    }


    static class Translator implements EventTranslatorOneArg<DisruptorEvent, Command> {
        @Override
        public void translateTo(DisruptorEvent event, long sequence, Command data) {
            event.set(data);
        }
    }
}
