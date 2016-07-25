package org.cheetah.fighter;


import org.cheetah.commons.utils.CollectionUtils;
import org.cheetah.fighter.plugin.Plugin;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

/**
 * Created by Max on 2016/2/2.
 */
public class FighterConfig {
    private List<Plugin> plugins;
    private List<Interceptor> interceptors;
    private List<DomainEventListener> eventListeners;
    private int eventPerformerSize;
    private int queueLength;
    private int ringBuffer;
    private int minThreads;
    private int maxThreads;
    private String engine;
    private String rejectionPolicy;

    public FighterConfig() {
        this.plugins = Collections.emptyList();
        this.interceptors = Collections.emptyList();
        this.eventListeners = Collections.emptyList();
    }

    public int getRingBuffer() {
        return ringBuffer;
    }

    public void setRingBuffer(int ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public List<Plugin> getPlugins() {
        return Lists.newArrayList(plugins);
    }

    public void setPlugins(List<Plugin> plugins) {
        this.plugins = plugins;
    }

    public List<Interceptor> getInterceptors() {
        return Lists.newArrayList(interceptors);
    }

    public void setInterceptors(List<Interceptor> interceptors) {
        this.interceptors = interceptors;
    }

    public List<DomainEventListener> getEventListeners() {
        return Lists.newArrayList(eventListeners);
    }

    public void setEventListeners(List<DomainEventListener> eventListeners) {
        this.eventListeners = eventListeners;
    }

    public int getEventPerformerSize() {
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

    public int getQueueLength() {
        return queueLength;
    }

    public void setQueueLength(int queueLength) {
        this.queueLength = queueLength;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public int getMinThreads() {
        return minThreads;
    }

    public void setMinThreads(int minThreads) {
        this.minThreads = minThreads;
    }

    public int getMaxThreads() {
        return maxThreads;
    }

    public void setMaxThreads(int maxThreads) {
        this.maxThreads = maxThreads;
    }

    public String getRejectionPolicy() {
        return rejectionPolicy;
    }

    public void setRejectionPolicy(String rejectionPolicy) {
        this.rejectionPolicy = rejectionPolicy;
    }
}
