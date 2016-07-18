package org.cheetah.fighter.core.governor;

import org.cheetah.commons.utils.Assert;
import org.cheetah.fighter.core.EventMessage;
import org.cheetah.fighter.core.Feedback;
import org.cheetah.fighter.core.handler.Handler;
import org.cheetah.fighter.plugin.PluginChain;

import java.util.EventListener;
import java.util.Map;

/**
 * Created by Max on 2016/3/7.
 */
public class AbstractGovernorAdapter implements Governor {
    private Governor adaptee;

    public AbstractGovernorAdapter(Governor governor, PluginChain pluginChain) {
        this.adaptee = enhance(governor, pluginChain);
    }

    @Override
    public Governor reset() {
        return adaptee.reset();
    }

    @Override
    public Governor initialize() {
        return adaptee.initialize();
    }

    @Override
    public Feedback command() {
        return adaptee.command();
    }

    @Override
    public Governor accept(EventMessage eventMessage) {
        return adaptee.accept(eventMessage);
    }

    @Override
    public EventMessage details() {
        return null;
    }


    @Override
    public String getId() {
        return adaptee.getId();
    }

    @Override
    public Governor registerHandlerSquad(Map<Class<? extends EventListener>, Handler> handlerMap) {
        return adaptee.registerHandlerSquad(handlerMap);
    }

    @Override
    public void unRegisterHandler(Handler handler) {
        adaptee.unRegisterHandler(handler);
    }

    @Override
    public Governor kagebunsin() throws CloneNotSupportedException {
        AbstractGovernorAdapter adapter = (AbstractGovernorAdapter) super.clone();
        adapter.setAdaptee(adaptee.kagebunsin());
        return adapter;
    }

    public Governor enhance(Governor governor, PluginChain pluginChain) {
        Assert.notNull(governor, "$adaptee must not be null");
        return (Governor) pluginChain.pluginAll(governor);
    }

    public void setAdaptee(Governor adaptee) {
        this.adaptee = adaptee;
    }

    public Governor adaptee() {
        return adaptee;
    }
}
