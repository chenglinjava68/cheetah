package org.cheetah.fighter;


/**
 * fighter的配置类
 * Created by Max on 2016/2/2.
 */
public class FighterConfig {
    /**
     * akka中每个事件需要创建的actor数量
     */
    private int eventPerformerSize;
    /**
     * 底层线程池队列长度
     */
    private int queueLength;
    /**
     * disruptor的RingBuffer长度
     */
    private int ringBuffer;
    /**
     * 底层线程池的线程数量
     */
    private int threadPoolSize;
    /**
     * fighter使用的引擎，默认使用Future
     */
    private String engine;
    /**
     * 线程池的拒绝策略：CALLER_RUNS、DISCARD_OLDEST、DISCARD、ABORT， 默认使用Abort
     */
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
