package org.cheetah.fighter.core.handler;

import org.cheetah.commons.logger.Info;
import org.cheetah.commons.logger.Warn;
import org.cheetah.commons.utils.ObjectUtils;
import org.cheetah.fighter.core.event.Event;

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
     * @param directive
     * @return
     */
    @Override
    public Feedback handle(Directive directive) {
        Feedback feedback = Feedback.SUCCESS;
        if (directive.feedback()) {
            feedback = completeExecute(directive.event());
            if (feedback.isFail())
                onFailure(directive);
            else
                onSuccess(directive);
        } else execute(directive.event());
        return feedback;
    }

    /**
     * 机器工作故障后的回调函数
     *
     * @param directive
     */
    @Override
    public void onFailure(Directive directive) {
        Warn.log(this.getClass(), "handler execute failure event is [" + directive.event() + "]");
        if (directive.callback() != null)
            directive.callback().call(false, directive.event().getSource());
    }

    /**
     * 机器工作故障后的回调函数
     *
     * @param directive
     */
    @Override
    public void onSuccess(Directive directive) {
        if (directive.callback() != null)
            directive.callback().call(true, directive.event().getSource());
    }

    @Override
    public Handler kagebunsin() throws CloneNotSupportedException {
        return (Handler) super.clone();
    }

    @Override
    public Handler kagebunsin(EventListener listener) throws CloneNotSupportedException {
        Handler handler = (Handler) super.clone();
        handler.setEventListener(this.eventListener);
        return handler;
    }

    /**
     * 无工作反馈的执行方式
     *
     * @param event
     */
    protected abstract void execute(Event event);

    /**
     * 有反馈的执行方式
     *
     * @param event
     * @return
     */
    protected abstract Feedback completeExecute(Event event);

    @Override
    public void setEventListener(EventListener eventListener) {
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
