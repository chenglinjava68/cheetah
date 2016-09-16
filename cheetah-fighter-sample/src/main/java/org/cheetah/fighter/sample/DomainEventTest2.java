package org.cheetah.fighter.sample;

import org.cheetah.fighter.DomainEvent;

/**
 * Created by Max on 2016/7/29.
 */
public class DomainEventTest2 extends DomainEvent<String> {
    public DomainEventTest2(String source) {
        super(source);
    }
}
