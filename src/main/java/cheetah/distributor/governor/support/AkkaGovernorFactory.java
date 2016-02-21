package cheetah.distributor.governor.support;

import cheetah.distributor.governor.Governor;
import cheetah.distributor.governor.GovernorFactory;

/**
 * Created by Max on 2016/2/21.
 */
public class AkkaGovernorFactory implements GovernorFactory {

    @Override
    public Governor createGovernor() {
        return new AkkaGovernor();
    }
}
