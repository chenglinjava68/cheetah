package cheetah.governor.support;

import cheetah.governor.Governor;
import cheetah.governor.GovernorFactory;

/**
 * Created by Max on 2016/2/21.
 */
public class AkkaGovernorFactory implements GovernorFactory {

    @Override
    public Governor createGovernor() {
        return new AkkaGovernor();
    }
}
