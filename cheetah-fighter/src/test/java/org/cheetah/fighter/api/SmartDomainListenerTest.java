package org.cheetah.fighter.api;

import org.cheetah.fighter.DomainEvent;
import org.cheetah.fighter.SmartDomainEventListener;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by maxhuang on 2016/7/18.
 */
public class SmartDomainListenerTest implements SmartDomainEventListener {
    public static final AtomicLong atomicLong1 = new AtomicLong();
    @Override
    public boolean supportsEventType(Class<? extends DomainEvent> eventType) {
        return eventType == EventPublisherTest.DomainEventTest.class;
    }

    @Override
    public boolean supportsSourceType(Class<?> sourceType) {
        return EventPublisherTest.User.class == sourceType;
    }

    @Override
    public void onDomainEvent(DomainEvent event) {
        System.out.println("SmartDomainListenerTest -- " + atomicLong1.incrementAndGet());
    }

    @Override
    public void onFinish() {
        System.out.println("on finish");
    }

    @Override
    public void onCancelled() {
        System.out.println("on cancelled");
    }

}
