package org.cheetah.fighter.governor;

import org.cheetah.fighter.core.governor.Governor;
import org.cheetah.fighter.core.governor.GovernorFactory;

/**
 * Created by Max on 2016/2/21.
 */
public class AkkaGovernorFactory implements GovernorFactory {

    @Override
    public Governor createGovernor() {
        return new AkkaGovernor();
    }
}
