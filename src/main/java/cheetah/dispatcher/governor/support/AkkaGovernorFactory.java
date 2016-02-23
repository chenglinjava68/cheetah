package cheetah.dispatcher.governor.support;

import cheetah.dispatcher.governor.Governor;
import cheetah.dispatcher.governor.GovernorFactory;

/**
 * Created by Max on 2016/2/21.
 */
public class AkkaGovernorFactory implements GovernorFactory {

    @Override
    public Governor createGovernor() {
        return new AkkaGovernor();
    }
}
