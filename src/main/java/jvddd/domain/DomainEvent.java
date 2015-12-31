package jvddd.domain;

import org.springframework.context.ApplicationEvent;

/**
 * Created by Max on 2015/12/22.
 */
public abstract class DomainEvent extends ApplicationEvent {

    public DomainEvent(Object source) {
        super(source);
    }
}
