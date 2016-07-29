package org.cheetah.fighter.governor;

import org.cheetah.commons.logger.Loggers;
import org.cheetah.commons.utils.Assert;
import org.cheetah.fighter.EventMessage;
import org.cheetah.fighter.Feedback;
import org.cheetah.fighter.handler.Handler;
import org.cheetah.fighter.plugin.PluginChain;

import java.util.List;

/**
 * Created by Max on 2016/3/7.
 */
@Deprecated
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
        long start = System.currentTimeMillis();
        Feedback feedback =  adaptee.command();
        Loggers.me().debugEnabled(this.getClass(), "消耗了{}毫秒", System.currentTimeMillis() - start);
        return feedback;
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
    public Governor registerHandlerSquad(List<Handler> handlers) {
        return adaptee.registerHandlerSquad(handlers);
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
