package cheetah.core;

import cheetah.governor.Governor;
import cheetah.governor.GovernorFactory;

/**
 * Created by Max on 2016/3/2.
 */
public class OrdinaryGovernorFactory implements GovernorFactory {
    @Override
    public Governor createGovernor() {
        return new OrdinaryGovernor();
    }
}
