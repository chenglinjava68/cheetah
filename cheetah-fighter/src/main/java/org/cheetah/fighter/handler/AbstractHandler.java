package org.cheetah.fighter.handler;

import org.cheetah.commons.utils.ObjectUtils;
import org.cheetah.fighter.DomainEvent;
import org.cheetah.fighter.DomainEventListener;
import org.cheetah.fighter.Event;
import org.cheetah.fighter.worker.Command;

import java.util.EventListener;

/**
 * Created by Max on 2016/2/14.
 */
public abstract class AbstractHandler implements Handler {
    private DomainEventListener<DomainEvent> eventListener;

    public AbstractHandler() {
    }

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
     * 某个消费者消费失败后的回调函数
     *
     * @param command
     */
    @Override
    public void onFailure(Command command, Throwable e) {
        DomainEventListener<DomainEvent> listener = getEventListener();
        listener.onCancelled(command.event(), e);
    }

    /**
     * 某个消费者消费成功后的回调函数
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
    public Handler kagebunsin(EventListener listener) throws CloneNotSupportedException {
        Handler handler = (Handler) super.clone();
        handler.registerEventListener(this.eventListener);
        return handler;
    }
    /**
     * 有反馈的执行方式
     *
     * @param event
     * @return
     */
    protected abstract boolean completeExecute(Event event);

    @Override
    public void registerEventListener(DomainEventListener<DomainEvent> eventListener) {
        this.eventListener = eventListener;
    }

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
