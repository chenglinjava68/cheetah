package org.cheetah.fighter.governor.support;

import org.cheetah.fighter.governor.Governor;
import org.cheetah.fighter.governor.GovernorFactory;

/**
 * Created by Max on 2016/2/21.
 */
public class AkkaGovernorFactory implements GovernorFactory {

    @Override
    public Governor createGovernor() {
        return new AkkaGovernor();
    }
}
