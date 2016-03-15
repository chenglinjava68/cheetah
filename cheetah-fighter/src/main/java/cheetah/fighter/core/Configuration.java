package cheetah.fighter.core;


import cheetah.fighter.commons.utils.CollectionUtils;
import cheetah.fighter.core.plugin.Plugin;

import java.util.Collections;
import java.util.EventListener;
import java.util.List;

/**
 * Created by Max on 2016/2/2.
 */
public class Configuration {
    private List<Plugin> plugins;
    private List<Interceptor> interceptors;
    private List<EventListener> eventListeners;
    private int eventPerformerSize;
    private int ringBufferSize;
    private String policy;

    public Configuration() {
        this.plugins = Collections.emptyList();
        this.interceptors = Collections.emptyList();
        this.eventListeners = Collections.emptyList();
    }

    public List<Plugin> plugins() {
        return Collections.unmodifiableList(plugins);
    }

    public void setPlugins(List<Plugin> plugins) {
        this.plugins = plugins;
    }

    public List<Interceptor> interceptors() {
        return Collections.unmodifiableList(interceptors);
    }

    public void setInterceptors(List<Interceptor> interceptors) {
        this.interceptors = interceptors;
    }

    public List<EventListener> eventListeners() {
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

    public String policy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }
}
