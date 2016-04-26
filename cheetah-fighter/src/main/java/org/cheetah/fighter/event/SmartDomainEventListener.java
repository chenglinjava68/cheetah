package org.cheetah.fighter.event;

import org.cheetah.domain.Entity;

/**
 * 一种聪明的监听器
 * 这种监听器会根据你的事件类型和源类型来检查是否有权处理该事件
 * 在我们的调度中心中，接收到一个事件时会为其寻找的相应的监听器，而Smart类型的监听器的优先级最高，如果找到Smart类型的监听器后
 * 会忽略掉普通的监听器（如：DomainEvent），使用者需要注意。
 * Created by Max on 2016/1/29.
 */
public interface SmartDomainEventListener extends DomainEventListener<DomainEvent> {
    boolean supportsEventType(Class<? extends DomainEvent> eventType);

    boolean supportsSourceType(Class<? extends Entity> sourceType);

    /**
     * 排序方法-在调度中寻找到相应的监听器后，会根据方法返回的值进行排序后
     * 再进行监听器的执行onDomainEvent
     * 值越小优先级就越大
     * @return
     */
    int getOrder();
}
