package org.cheetah.fighter.handler;

import org.cheetah.commons.logger.Debug;
import org.cheetah.commons.logger.Error;
import org.cheetah.fighter.event.Event;

import java.util.EventListener;
import java.util.Objects;

/**
 * 每个lisnter都会配一Machine负责监控和处理
 * Created by Max on 2016/2/1.
 */
public interface Handler extends Cloneable {

    /**
     * 给机器发送一个指令，让其工作
     *
     * @param directive
     * @return
     */
    default Feedback handle(Directive directive) {
        Feedback feedback = Feedback.FAILURE;
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
    default void onFailure(Directive directive) {
        Error.log(this.getClass(), "Machine execute failure event is [" + directive + "]");
        if (Objects.nonNull(directive.callback()))
            directive.callback().call(false, directive.event().getSource());
    }

    /**
     * 机器工作故障后的回调函数
     *
     * @param directive
     */
    default void onSuccess(Directive directive) {
        Debug.log(this.getClass(), "Machine execute success event is [" + directive + "]");
        if (Objects.nonNull(directive.callback()))
            directive.callback().call(true, directive.event().getSource());
    }

    /**
     * 无工作反馈的执行方式
     *
     * @param event
     */
    void execute(Event event);

    /**
     * 有反馈的执行方式
     *
     * @param event
     * @return
     */
    Feedback completeExecute(Event event);

    void setEventListener(EventListener eventListener);

    EventListener getEventListener();

    Handler kagebunsin() throws CloneNotSupportedException;

    Handler kagebunsin(EventListener listener) throws CloneNotSupportedException;

}
