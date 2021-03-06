package org.cheetah.fighter.handler;

import org.cheetah.commons.utils.ObjectUtils;
import org.cheetah.fighter.DomainEvent;
import org.cheetah.fighter.DomainEventListener;
import org.cheetah.fighter.Event;
import org.cheetah.fighter.worker.Command;

/**
 * Created by Max on 2016/2/14.
 */
@Deprecated
public abstract class AbstractHandler implements Handler {
    private final DomainEventListener<DomainEvent> eventListener;

    public AbstractHandler(DomainEventListener<DomainEvent> eventListener) {
        this.eventListener = eventListener;
    }

    /**
     * 给机器发送一个指令，让其工作
     *
     * @param command
     * @return
     */
    @Override
    public boolean handle(Command command) {
        return completeExecute(command.event());
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

    /**
     * 有反馈的执行方式
     *
     * @param event
     * @return
     */
    protected abstract boolean completeExecute(Event event);

    @Override
    public DomainEventListener<DomainEvent> getEventListener() {
        return eventListener;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractHandler that = (AbstractHandler) o;

        return ObjectUtils.nullSafeEquals(this.eventListener, that.eventListener);
    }

    @Override
    public int hashCode() {
        return ObjectUtils.nullSafeHashCode(this.eventListener) * 29;
    }

}
