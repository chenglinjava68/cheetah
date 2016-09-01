package org.cheetah.bootstrap.controller;

import org.cheetah.fighter.api.DomainEvent;

/**
 * Created by Max on 2016/3/1.
 */
public class ApplicationEventTest extends DomainEvent {

    public ApplicationEventTest(Object source) {
        super(source);
    }
}
