package org.cheetah.fighter.governor;

import org.cheetah.fighter.event.Event;
import org.cheetah.fighter.handler.Feedback;
import org.cheetah.fighter.handler.Handler;

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
     * 注册负责管理的事件
     * @param $event
     * @return
     */
    Governor registerEvent(Event $event);

    /**
     * 获取管理者唯一的标示符
     * @return
     */
    String getId();

    /**
     * 是否需要第一个消费者必须成功后才执行之后的消费者，
     * 需要使用该功能时需要使用Smart类型的事件监听器
     * @param $fisrtSucceed
     * @return
     */
    Governor setFisrtSucceed(boolean $fisrtSucceed);

    /**
     * 注册一组工作机器
     * @param handlerMap
     * @return
     */
    Governor registerHandlerSquad(Map<Class<? extends EventListener>, Handler> handlerMap);

    /**
     * 是否要返回结果
     * @param $needResult
     * @return
     */
    Governor setNeedResult(boolean $needResult);

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
