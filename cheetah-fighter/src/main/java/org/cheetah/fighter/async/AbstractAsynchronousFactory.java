package org.cheetah.fighter.async;

import com.google.common.collect.Sets;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.cheetah.commons.logger.Info;
import org.cheetah.commons.utils.StringUtils;
import org.cheetah.fighter.worker.WorkerFactory;

import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;

/**
 * Created by Max on 2016/5/3.
 */
public abstract class AbstractAsynchronousFactory<T> implements AsynchronousFactory<T> {
    private int minThreahs = Runtime.getRuntime().availableProcessors();
    private int maxThreahs = Runtime.getRuntime().availableProcessors() * 2;
    private int queueLength;
    private final static int DEFAULT_QUEUE_LENGTH = 100000;
    private RejectedExecutionHandler rejectedExecutionHandler = new AbortPolicy();
    private String rejectionPolicy;
    private Set<ExecutorService> executorServices = Sets.newConcurrentHashSet();
    private WorkerFactory workerFactory;

    @Override
    public void start() {
    }

    @Override
    public void stop() {
        if (executorServices != null) {
            this.executorServices.forEach(executorService -> {
                while (!executorService.isShutdown()) {
                    executorService.shutdown();
                }
            });
            executorServices = null;
        }
    }

    protected synchronized ExecutorService buildThreadPool() {
        BlockingQueue<Runnable> blockingQueue;
        if (queueLength > 0)
            blockingQueue = new LinkedBlockingQueue<>(queueLength);
        else
            blockingQueue = new LinkedBlockingQueue<>(DEFAULT_QUEUE_LENGTH);

        ThreadPoolExecutor executorService = new ThreadPoolExecutor(minThreahs, maxThreahs,
                3000L, TimeUnit.MILLISECONDS, blockingQueue,
                new ThreadFactoryBuilder().setNameFormat("Cheetah-Fighter-%d").build(), rejectedExecutionHandler);
        Info.log(this.getClass(), "build executor min threahs size {}, max threahs size {}, keep alive time {} ms, queue length {}, rejection policy {}",
                minThreahs, maxThreahs, 30000, queueLength, StringUtils.isBlank(rejectionPolicy) ? "Abort" : rejectionPolicy);
        executorServices.add(executorService);
        return executorService;
    }

    public void setMinThreahs(int minThreahs) {
        this.minThreahs = minThreahs;
    }

    public void setMaxThreahs(int maxThreahs) {
        this.maxThreahs = maxThreahs;
    }

    public void setQueueLength(int queueLength) {
        this.queueLength = queueLength;
    }

    public void setWorkerFactory(WorkerFactory workerFactory) {
        this.workerFactory = workerFactory;
    }

    public WorkerFactory getWorkerFactory() {
        return workerFactory;
    }

    public void setRejectionPolicy(String rejectionPolicy) {
        switch (rejectionPolicy) {
            case "CALLER_RUNS":
                this.rejectedExecutionHandler = new ThreadPoolExecutor.CallerRunsPolicy();
                break;
            case "DISCARD_OLDEST":
                this.rejectedExecutionHandler = new ThreadPoolExecutor.DiscardOldestPolicy();
                break;
            case "DISCARD":
                this.rejectedExecutionHandler = new ThreadPoolExecutor.DiscardPolicy();
                break;
            default:
                this.rejectedExecutionHandler = new AbortPolicy();
        }
        this.rejectionPolicy = StringUtils.isBlank(rejectionPolicy) ? "Abort" : rejectionPolicy;
    }
}
