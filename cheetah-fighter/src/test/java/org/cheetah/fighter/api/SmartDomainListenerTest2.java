package org.cheetah.fighter.api;

import org.cheetah.commons.utils.ArithUtils;
import org.cheetah.fighter.core.event.DomainEvent;
import org.cheetah.fighter.core.event.SmartDomainEventListener;

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
        System.out.println("SmartDomainListenerTest2 -- " + atomicLong1.incrementAndGet());

    }

    @Override
    public void onCancelled() {

    }

    @Override
    public void onFinish() {
        System.out.println("SmartDomainListenerTest2 -- onFinish-" + atomicLong2.incrementAndGet());
    }
}
