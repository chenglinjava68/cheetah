package org.cheetah.fighter.api;


import org.cheetah.commons.utils.IDGenerator;

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
