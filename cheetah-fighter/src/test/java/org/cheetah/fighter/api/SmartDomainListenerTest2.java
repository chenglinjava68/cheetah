package org.cheetah.fighter.api;

import java.util.concurrent.RejectedExecutionException;
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
        while(i < 1000000)
            i++;
        System.out.println("SmartDomainListenerTest2 -- " + atomicLong1.incrementAndGet() + "----" + atomicLong2.get());
        throw new RuntimeException();

    }

    @Override
    public void onCancelled(DomainEvent domainEvent, Throwable e) {
        if(e instanceof RejectedExecutionException)
            System.out.println("SmartDomainListenerTest2 -- onCancelled-" + atomicLong2.incrementAndGet());
    }

    @Override
    public void onFinish(DomainEvent domainEvent) {
    }
}
