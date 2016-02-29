package cheetah.governor;

import cheetah.event.Event;
import cheetah.machine.Feedback;
import cheetah.machine.Machine;
import cheetah.plugin.InterceptorChain;

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
     * 设置负责管理的事件
     * @param $event
     * @return
     */
    Governor setEvent(Event $event);

    /**
     * 获取管理的事件
     * @return
     */
    Event getEven();

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
    Governor setFisrtSucceed(Boolean $fisrtSucceed);

    /**
     * 注册一组工作机器
     * @param $workers
     * @return
     */
    Governor registerMachineSquad(Map<Class<? extends EventListener>, Machine> $workers);

    /**
     * 是否要返回结果
     * @param $needResult
     * @return
     */
    Governor setNeedResult(boolean $needResult);

    /**
     * 开除一个工作机器
     * @param machine
     */
    void expelMachine(Machine machine);

    /**
     * 设置拦截器链
     * @param $interceptorChain
     */
    void setInterceptorChain(InterceptorChain $interceptorChain);

    /**
     * 分身术-即拷贝一个管理者
     * @return
     * @throws CloneNotSupportedException
     */
    Object kagebunsin() throws CloneNotSupportedException;

}
