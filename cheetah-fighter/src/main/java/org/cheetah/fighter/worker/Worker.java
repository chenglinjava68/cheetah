package org.cheetah.fighter.worker;

import org.cheetah.fighter.api.Feedback;
import org.cheetah.fighter.api.Interceptor;

import java.util.List;

/**
 * 事件工作者
 * Created by Max on 2016/2/19.
 */
public interface Worker extends Cloneable {
    /**
     * 根据接受到命令开始工作
     * @param command
     */
    Feedback work(Command command);

    List<Interceptor> getInterceptors();
}
