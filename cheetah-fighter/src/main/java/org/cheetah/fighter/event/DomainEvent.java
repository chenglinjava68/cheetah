package org.cheetah.fighter.event;


import org.cheetah.domain.Entity;

/**
 * Created by Max on 2016/1/29.
 */
public abstract class DomainEvent extends Event<Entity> {
    public DomainEvent(Entity source) {
        super(source);
    }
}
