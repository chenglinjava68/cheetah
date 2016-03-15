package cheetah.fighter.governor.support;

import cheetah.fighter.governor.Governor;
import cheetah.fighter.governor.GovernorFactory;

/**
 * Created by Max on 2016/2/21.
 */
public class AkkaGovernorFactory implements GovernorFactory {

    @Override
    public Governor createGovernor() {
        return new AkkaGovernor();
    }
}
