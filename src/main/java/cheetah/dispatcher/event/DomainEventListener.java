package cheetah.dispatcher.event;

import java.util.EventListener;

/**
 * Created by Max on 2016/1/29.
 */
public interface DomainEventListener<E extends DomainEvent> extends EventListener{
    void onDomainEvent(E event);
}
