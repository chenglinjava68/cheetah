package org.cheetah.fighter.api;

import org.cheetah.fighter.DomainEvent;
import org.cheetah.fighter.SmartDomainEventListener;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by maxhuang on 2016/7/18.
 */
public class SmartDomainListenerTest2 implements SmartDomainEventListener {
    public static final AtomicLong atomicLong1 = new AtomicLong();
    public static final AtomicLong atomicLong2 = new AtomicLong();

    @Override
    public boolean supportsEventType(Class<? extends DomainEvent> eventType) {
        return eventType == EventPublisherTest.DomainEventTest2.class;
    }

    @Override
    public boolean supportsSourceType(Class<?> sourceType) {
        return EventPublisherTest.User.class == sourceType;
    }

    @Override
    public void onDomainEvent(DomainEvent event) {
        int i = 0;
        while(i < 10000000)
            i++;
        System.out.println("SmartDomainListenerTest2 -- " + atomicLong1.incrementAndGet());

    }

    @Override
    public void onCancelled(DomainEvent domainEvent, Throwable e) {

    }

    @Override
    public void onFinish(DomainEvent domainEvent) {
        System.out.println("SmartDomainListenerTest2 -- onFinish-" + atomicLong2.incrementAndGet());
    }
}
