package cheetah.fighter.governor.support;

import akka.actor.ActorRef;
import cheetah.fighter.core.plugin.PluginChain;
import cheetah.fighter.governor.AbstractGovernorAdapter;

/**
 * Created by Max on 2016/3/7.
 */
public class AkkaGovernorAdapter extends AbstractGovernorAdapter {
    public AkkaGovernorAdapter(AkkaGovernor governor, PluginChain pluginChain) {
        super(governor, pluginChain);
    }

    public void setWorker(ActorRef worker) {
        ((AkkaGovernor) adaptee()).setWorker(worker);
    }
}
