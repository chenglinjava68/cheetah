package org.cheetah.fighter.core.governor;

import org.cheetah.fighter.core.EventMessage;
import org.cheetah.fighter.core.handler.Feedback;
import org.cheetah.fighter.core.handler.Handler;

import java.util.EventListener;
import java.util.Map;

/**
 * 事件管理者接口
 * Created by Max on 2016/2/20.
 */
public interface Governor extends Cloneable {

    Governor reset();

    /**
     * 重置内部管理的数据
     * @return
     */
    Governor initialize();

    /**
     * 管理者下达一个命令让工人开始工作
     * @return
     */
    Feedback command();

    /**
     * 承担当前消息的任务
     * @param eventMessage
     * @return
     */
    Governor accept(EventMessage eventMessage);

    EventMessage details();

    /**
     * 获取管理者唯一的标示符
     * @return
     */
    String getId();

    /**
     * 注册一组工作机器
     * @param handlerMap
     * @return
     */
    Governor registerHandlerSquad(Map<Class<? extends EventListener>, Handler> handlerMap);

    /**
     * 开除一个工作机器
     * @param handler
     */
    void expelHandler(Handler handler);

    /**
     * 分身术-即拷贝一个管理者
     * @return
     * @throws CloneNotSupportedException
     */
    Governor kagebunsin() throws CloneNotSupportedException;

}
