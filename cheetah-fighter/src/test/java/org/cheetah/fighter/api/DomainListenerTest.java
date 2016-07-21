package org.cheetah.fighter.api;

import org.cheetah.commons.utils.ArithUtils;
import org.cheetah.fighter.DomainEvent;
import org.cheetah.fighter.DomainEventListener;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by maxhuang on 2016/7/18.
 */
public class DomainListenerTest implements DomainEventListener {
    public static final AtomicLong atomicLong3 = new AtomicLong();

    @Override
    public void onDomainEvent(DomainEvent event) {
        if(!(event instanceof EventPublisherTest.DomainEventTest))
            return ;
        double v = ArithUtils.round(Math.random() * 100, 0);
        long i = ArithUtils.convertsToLong(v);
//            try {
//                Thread.sleep(i);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        int k = 100000;
        while (k > 0) {
            k--;
        }
        System.out.println("DomainListenerTest -- " + atomicLong3.incrementAndGet());
    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onCancelled() {

    }

}
