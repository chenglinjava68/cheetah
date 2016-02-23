package cheetah.dispatcher.event;

import cheetah.domain.Entity;

/**
 * Created by Max on 2016/1/29.
 */
public interface SmartDomainEventListener<E extends DomainEvent> extends DomainEventListener<E> {
    boolean supportsEventType(Class<? extends DomainEvent> eventType);

    boolean supportsSourceType(Class<? extends Entity> sourceType);

    int getOrder();
}
