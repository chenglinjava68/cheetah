package org.cheetah.fighter.api;

import org.cheetah.fighter.DomainEventListener;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by maxhuang on 2016/7/18.
 */
public class DomainListenerTest2 implements DomainEventListener<EventPublisherTest.ApplicationEventTest> {
    public static final AtomicLong atomicLong3 = new AtomicLong();

    @Override
    public void onDomainEvent(EventPublisherTest.ApplicationEventTest event) {
        System.out.println("DomainListenerTest2 -- " + atomicLong3.incrementAndGet());
        throw new RuntimeException();
    }

    @Override
    public void onFinish(EventPublisherTest.ApplicationEventTest event) {
        System.out.println("onfinish:" );
    }

    @Override
    public void onCancelled(EventPublisherTest.ApplicationEventTest event, Throwable e) {
        System.out.println("oncacelled:");
    }


}
