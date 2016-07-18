package org.cheetah.fighter.api;

import org.cheetah.commons.utils.ArithUtils;
import org.cheetah.fighter.core.event.DomainEvent;
import org.cheetah.fighter.core.event.SmartDomainEventListener;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by maxhuang on 2016/7/18.
 */
public class SmartDomainListenerTest2 implements SmartDomainEventListener {
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
//            try {
//                Thread.sleep(100000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        double v = ArithUtils.round(Math.random() * 100, 0);
        long i = ArithUtils.convertsToLong(v);
        System.out.println(System.currentTimeMillis());
        System.out.println("SmartDomainListenerTest2 -- " + atomicLong2.incrementAndGet());
    }

    @Override
    public void onCancelled(DomainEvent event) {

    }

    @Override
    public void onFinish(DomainEvent event) {
        System.out.println("SmartDomainListenerTest2 -- onFinish");
    }
}
