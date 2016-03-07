package cheetah.governor.support;

import akka.actor.ActorRef;
import cheetah.core.plugin.PluginChain;
import cheetah.governor.AbstractGovernorAdapter;

/**
 * Created by Max on 2016/3/7.
 */
public class AkkaGovernorAdapter extends AbstractGovernorAdapter {
    public AkkaGovernorAdapter(AkkaGovernor governor, PluginChain pluginChain) {
        super(governor, pluginChain);
    }

    public void setWorker(ActorRef worker) {
        ((AkkaGovernor) getAdaptee()).setWorker(worker);
    }
}
