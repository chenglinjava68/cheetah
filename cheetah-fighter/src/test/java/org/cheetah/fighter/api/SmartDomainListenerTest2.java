package org.cheetah.fighter.api;

import org.cheetah.commons.utils.ArithUtils;
import org.cheetah.fighter.core.event.DomainEvent;
import org.cheetah.fighter.core.event.SmartDomainEventListener;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by maxhuang on 2016/7/18.
 */
public class SmartDomainListenerTest2 implements SmartDomainEventListener {
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
//            try {
//                Thread.sleep(100000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        double v = ArithUtils.round(Math.random() * 100, 0);
        long i = ArithUtils.convertsToLong(v);
        try {
            Thread.sleep(111111111);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("SmartDomainListenerTest2 -- " + atomicLong3.incrementAndGet());
    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onCancelled() {

    }
}
