package org.cheetah.fighter;

import java.io.Serializable;
import java.util.EventListener;

/**
 * 领域事件的消费者接口
 * Created by Max on 2016/1/29.
 */
public interface DomainEventListener<E extends DomainEvent> extends EventListener, Serializable {
    /**
     * 消费方法，为一个事件执行相对应的逻辑，当需要为一个事件创建一个listener时，
     * 需要将其的逻辑封装在本方法中
     * @param event
     */
    void onDomainEvent(E event);

    /**
     * 当onDomainEvent执行完成，并且没有抛出异常时，将会进入到该方法
     * @param event
     */
    void onFinish(E event);

    /**
     * 当onDomainEvent执行失败，即产生了异常，将会进入这个方法
     * @param event
     * @param e
     */
    void onCancelled(E event, Throwable e);
}
