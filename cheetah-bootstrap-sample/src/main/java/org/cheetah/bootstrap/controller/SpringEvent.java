package org.cheetah.bootstrap.controller;

import org.springframework.context.ApplicationEvent;

/**
 * Created by Max on 2016/7/25.
 */
public class SpringEvent extends ApplicationEvent {

    public SpringEvent(Object source) {
        super(source);
    }
}
