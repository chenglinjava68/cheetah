package org.cheetah.fighter.worker;

import org.cheetah.fighter.EventMessage;
import org.cheetah.fighter.Feedback;

/**
 * Created by Max on 2016/7/21.
 */
public interface WorkerAdapter {

    boolean supports(Object object);

    Feedback work(EventMessage eventMessage);
}
