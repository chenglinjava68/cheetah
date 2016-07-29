package org.cheetah.fighter;

/**
 * 一种聪明的监听器
 * 这种监听器会根据你的事件类型和源类型来检查是否有权处理该事件
 * 在我们的调度中心中，接收到一个事件时会为其寻找的相应的监听器，而Smart类型的监听器的优先级最高，如果找到Smart类型的监听器后
 * 会忽略掉普通的监听器（如：DomainEvent），使用者需要注意。
 * Created by Max on 2016/1/29.
 */
public interface SmartDomainEventListener extends DomainEventListener<DomainEvent> {
    boolean supportsEventType(Class<? extends DomainEvent> eventType);

    boolean supportsSourceType(Class<?> sourceType);
}
