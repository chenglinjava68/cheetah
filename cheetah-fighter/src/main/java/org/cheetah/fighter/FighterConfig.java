package org.cheetah.fighter;


/**
 * Created by Max on 2016/2/2.
 */
public class FighterConfig {

    private int eventPerformerSize;
    private int queueLength;
    private int ringBuffer;
    private int threadPoolSize;
    private String engine;
    private String rejectionPolicy;

    public FighterConfig() {

    }

    public int getRingBuffer() {
        return ringBuffer;
    }

    public void setRingBuffer(int ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public int getEventPerformerSize() {
        return eventPerformerSize;
    }

    public void setEventPerformerSize(int eventPerformerSize) {
        this.eventPerformerSize = eventPerformerSize;
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

    public int getThreadPoolSize() {
        return threadPoolSize;
    }

    public void setThreadPoolSize(int threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
    }

    public String getRejectionPolicy() {
        return rejectionPolicy;
    }

    public void setRejectionPolicy(String rejectionPolicy) {
        this.rejectionPolicy = rejectionPolicy;
    }
}
