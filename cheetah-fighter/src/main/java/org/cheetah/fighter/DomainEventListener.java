package org.cheetah.fighter;

import java.io.Serializable;
import java.util.EventListener;

/**
 * Created by Max on 2016/1/29.
 */
public interface DomainEventListener<E extends DomainEvent> extends EventListener, Serializable {
    void onDomainEvent(E event);

    void onFinish(E event);

    void onCancelled(E event, Throwable e);
}
