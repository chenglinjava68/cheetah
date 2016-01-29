package cheetah.event;


import cheetah.domain.Entity;

/**
 * Created by Max on 2016/1/29.
 */
public abstract class DomainEvent<E extends Entity> implements Event<E> {
    private Long occurredTime = System.currentTimeMillis();
    private E source;

    public DomainEvent(E source) {
        this.source = source;
    }

    @Override
    public final Long occurredTime() {
        return occurredTime;
    }

}
