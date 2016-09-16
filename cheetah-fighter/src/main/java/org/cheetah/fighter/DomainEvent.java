package org.cheetah.fighter;


import org.cheetah.commons.utils.IDGenerator;
import org.cheetah.fighter.Event;

/**
 * 领域事件的抽象类
 * Created by Max on 2016/1/29.
 */
public abstract class DomainEvent<T> extends Event<T> {
    private String id;
    public DomainEvent(T source) {
        super(source);
        this.id = IDGenerator.generateId();
    }

    public String getId() {
        return id;
    }
}
