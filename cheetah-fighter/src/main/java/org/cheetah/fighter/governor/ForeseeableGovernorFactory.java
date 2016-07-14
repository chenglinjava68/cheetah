package org.cheetah.fighter.governor;

import org.cheetah.fighter.core.governor.Governor;
import org.cheetah.fighter.core.governor.GovernorFactory;

/**
 * Created by Max on 2016/3/2.
 */
public class ForeseeableGovernorFactory implements GovernorFactory {
    @Override
    public Governor createGovernor() {
        return new ForeseeableGovernor();
    }
}
