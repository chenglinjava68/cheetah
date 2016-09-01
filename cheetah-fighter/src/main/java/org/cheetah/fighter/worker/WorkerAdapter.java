package org.cheetah.fighter.worker;

import org.cheetah.fighter.api.EventMessage;
import org.cheetah.fighter.api.Feedback;

/**
 * worker适配器
 * Created by Max on 2016/7/21.
 */
public interface WorkerAdapter extends Cloneable{

    /**
     * 支持的类型，按引擎为准
     * @param object
     * @return
     */
    @Deprecated
    boolean supports(Object object);

    Feedback work(EventMessage eventMessage);

}
