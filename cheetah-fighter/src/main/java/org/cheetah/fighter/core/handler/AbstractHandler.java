package org.cheetah.fighter.core.handler;

import org.cheetah.commons.logger.Warn;
import org.cheetah.commons.utils.ObjectUtils;
import org.cheetah.fighter.core.event.DomainEvent;
import org.cheetah.fighter.core.event.DomainEventListener;
import org.cheetah.fighter.core.event.Event;
import org.cheetah.fighter.core.worker.Command;

import java.util.EventListener;

/**
 * Created by Max on 2016/2/14.
 */
public abstract class AbstractHandler implements Handler {
    private EventListener eventListener;

    public AbstractHandler() {
    }

    public AbstractHandler(EventListener eventListener) {
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
    public void onFailure(Command command) {
        Warn.log(this.getClass(), "handler execute failure event is [" + command.event() + "]");
        DomainEventListener<DomainEvent> listener = (DomainEventListener<DomainEvent>) getEventListener();
        listener.onCancelled();
    }

    /**
     * 某个消费者消费成功后的回调函数
     * @param command
     */
    @Override
    public void onSuccess(Command command) {
        Warn.log(this.getClass(), "handler execute failure event is [" + command.event() + "]");
        DomainEventListener<DomainEvent> listener = (DomainEventListener<DomainEvent>) getEventListener();
        listener.onFinish();
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
    public void registerEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    @Override
    public EventListener getEventListener() {
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
