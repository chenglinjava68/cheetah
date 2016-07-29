package org.cheetah.fighter.governor;

import org.cheetah.commons.utils.Assert;
import org.cheetah.commons.utils.IDGenerator;
import org.cheetah.fighter.EventMessage;
import org.cheetah.fighter.Feedback;
import org.cheetah.fighter.handler.Handler;

import java.util.List;

/**
 * Created by Max on 2016/3/2.
 */
@Deprecated
public abstract class AbstractGovernor implements Governor {
    private String id;
    private EventMessage eventMessage;
    private List<Handler> handlers;

    @Override
    public Governor reset() {
        this.handlers = null;
        this.eventMessage = null;
        return this;
    }

    @Override
    public Governor initialize() {
        this.id = IDGenerator.generateId();
        return this;
    }

    @Override
    public Feedback command() {
        return notifyAllWorker();
    }

    protected abstract Feedback notifyAllWorker();

    @Override
    public Governor accept(EventMessage eventMessage) {
        this.eventMessage = eventMessage;
        return this;
    }

    @Override
    public EventMessage details() {
        return eventMessage;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Governor registerHandlerSquad(List<Handler> handlers) {
        this.handlers = handlers;
        return this;
    }

    @Override
    public void unRegisterHandler(Handler handler) {
        Assert.notNull(handler, "handler must not be null");
        handlers.remove(handler);
    }

    @Override
    public Governor kagebunsin() throws CloneNotSupportedException {
        return (Governor) super.clone();
    }

    public String id() {
        return id;
    }

    protected List<Handler> handlers() {
        return handlers;
    }

}
