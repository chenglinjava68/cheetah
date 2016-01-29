package cheetah.event;

/**
 * Created by Max on 2016/1/29.
 */
public interface StatefulDomainEventListener<E extends DomainEvent> extends SmartDomainEventListener<E> {
}
