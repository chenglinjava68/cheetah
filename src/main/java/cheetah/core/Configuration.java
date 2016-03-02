package cheetah.core;


import cheetah.plugin.Interceptor;
import cheetah.util.CollectionUtils;

import java.util.Collections;
import java.util.EventListener;
import java.util.List;

/**
 * Created by Max on 2016/2/2.
 */
public class Configuration {
    private List<Interceptor> plugins;
    private List<EventListener> eventListeners;
    private int eventPerformerSize;
    private int ringBufferSize;
    public Configuration() {
        this.plugins = Collections.EMPTY_LIST;
        this.eventListeners = Collections.EMPTY_LIST;
    }

    public List<Interceptor> getPlugins() {
        return Collections.unmodifiableList(plugins);
    }

    public void setPlugins(List<Interceptor> plugins) {
        this.plugins = plugins;
    }

    public List<EventListener> getEventListeners() {
        return Collections.unmodifiableList(eventListeners);
    }

    public void setEventListeners(List<EventListener> eventListeners) {
        this.eventListeners = eventListeners;
    }

    public int eventPerformerSize() {
        return eventPerformerSize;
    }

    public void setEventPerformerSize(int eventPerformerSize) {
        this.eventPerformerSize = eventPerformerSize;
    }

    public boolean hasPlugin() {
        return !CollectionUtils.isEmpty(this.plugins);
    }

    public boolean hasListener() {
        return !CollectionUtils.isEmpty(this.eventListeners);
    }

    public int ringBufferSize() {
        return ringBufferSize;
    }

    public void setRingBufferSize(int ringBufferSize) {
        this.ringBufferSize = ringBufferSize;
    }
}
