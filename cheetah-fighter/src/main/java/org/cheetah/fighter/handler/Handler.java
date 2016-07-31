package org.cheetah.fighter.handler;

import org.cheetah.fighter.DomainEvent;
import org.cheetah.fighter.DomainEventListener;
import org.cheetah.fighter.worker.Command;

/**
 * 每个lisnter都会配一Machine负责监控和处理
 * Created by Max on 2016/2/1.
 */
public interface Handler extends Cloneable {

    /**
     * 给worker发送一个命令，让其工作
     *
    * @param command
    * @return
            */
    boolean handle(Command command);

    /**
     * worker工作故障后的回调函数
     *
     * @param command
     */
    void onFailure(Command command, Throwable e);

    /**
     * worker工作故障后的回调函数
     *
     * @param command
     */
    void onSuccess(Command command);

    /**
     * 获取eventListener
     * @return
     */
    DomainEventListener<DomainEvent> getEventListener();

    /**
     * 复制一个handler
     * @return
     * @throws CloneNotSupportedException
     */
    Handler kagebunsin() throws CloneNotSupportedException;

}
