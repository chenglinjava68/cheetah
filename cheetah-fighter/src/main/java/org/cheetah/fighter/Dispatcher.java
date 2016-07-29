package org.cheetah.fighter;

/**
 * 调度抽象类
* Created by Max on 2016/2/23.
*/
public abstract class Dispatcher {
    /**
     * 接收一个事件消息
     * @param eventMessage
     * @return
     */
    public abstract EventResult receive(final EventMessage eventMessage);

    protected abstract EventResult dispatch();
}
