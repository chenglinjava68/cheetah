package cheetah.fighter.governor.support;

import cheetah.fighter.governor.Governor;
import cheetah.fighter.governor.GovernorFactory;

/**
 * Created by Max on 2016/3/2.
 */
public class OrdinaryGovernorFactory implements GovernorFactory {
    @Override
    public Governor createGovernor() {
        return new OrdinaryGovernor();
    }
}
