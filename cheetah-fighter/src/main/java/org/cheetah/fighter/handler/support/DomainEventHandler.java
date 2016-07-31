package org.cheetah.fighter.handler.support;

import org.cheetah.common.logger.Debug;
import org.cheetah.common.logger.Err;
import org.cheetah.common.utils.ObjectUtils;
import org.cheetah.fighter.DomainEvent;
import org.cheetah.fighter.DomainEventListener;
import org.cheetah.fighter.Event;
import org.cheetah.fighter.handler.Handler;
import org.cheetah.fighter.worker.Command;

/**
 * 领域事件处理器实现类
 * Created by Max on 2016/2/1.
 */
public class DomainEventHandler implements Handler {
    private final DomainEventListener<DomainEvent> eventListener;

    public DomainEventHandler(DomainEventListener<DomainEvent> eventListener) {
        this.eventListener = eventListener;
    }

    protected void doExecute(Event event) {
        Debug.log(this.getClass(), "DomainEventHandler do Execute event {}", event);
        DomainEvent $event = (DomainEvent) event;
        DomainEventListener<DomainEvent> listener = getEventListener();
        listener.onDomainEvent($event);
    }

    /**
     * 有反馈的执行方式
     *
     * @param event
     * @return
     */
    @Deprecated
    protected boolean completeExecute(Event event) {
        try {
            doExecute(event);
        } catch (Throwable e) {
            Err.log(this.getClass(), "event handler completeExecute error", e);
            return false;
        }
        return true;
    }

    /**
     * 给机器发送一个指令，让其工作
     *
     * @param command
     * @return
     */
    @Override
    public boolean handle(Command command) {
        doExecute(command.event());
        return true;
    }

    /**
     * 消费者消费失败后的回调函数
     *
     * @param command
     */
    @Override
    public void onFailure(Command command, Throwable e) {
        DomainEventListener<DomainEvent> listener = getEventListener();
        listener.onCancelled(command.event(), e);
    }

    /**
     * 消费者消费成功后的回调函数
     *
     * @param command
     */
    @Override
    public void onSuccess(Command command) {
        DomainEventListener<DomainEvent> listener = getEventListener();
        listener.onFinish(command.event());
    }

    @Override
    public Handler kagebunsin() throws CloneNotSupportedException {
        return (Handler) super.clone();
    }

    @Override
    public DomainEventListener<DomainEvent> getEventListener() {
        return eventListener;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DomainEventHandler that = (DomainEventHandler) o;

        return ObjectUtils.nullSafeEquals(this.eventListener, that.eventListener);
    }

    @Override
    public int hashCode() {
        return ObjectUtils.nullSafeHashCode(this.eventListener) * 29;
    }

    @Override
    public String toString() {
        return "{" +
                "eventListener=" + eventListener.getClass().getName() +
                '}';
    }
}
