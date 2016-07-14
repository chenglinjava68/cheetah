package org.cheetah.fighter.core.handler;

import java.util.EventListener;

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
    Feedback handle(Directive directive);

    /**
     * 机器工作故障后的回调函数
     *
     * @param directive
     */
    void onFailure(Directive directive);

    /**
     * 机器工作故障后的回调函数
     *
     * @param directive
     */
    void onSuccess(Directive directive);

    void setEventListener(EventListener eventListener);

    EventListener getEventListener();

    Handler kagebunsin() throws CloneNotSupportedException;

    Handler kagebunsin(EventListener listener) throws CloneNotSupportedException;

}
