package org.cheetah.fighter.api;

import org.cheetah.fighter.core.event.DomainEvent;
import org.cheetah.fighter.core.event.SmartDomainEventListener;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by maxhuang on 2016/7/18.
 */
public class SmartDomainListenerTest implements SmartDomainEventListener {
    public static final AtomicLong atomicLong3 = new AtomicLong();
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
        System.out.println("SmartDomainListenerTest -- " + atomicLong3.incrementAndGet());
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
