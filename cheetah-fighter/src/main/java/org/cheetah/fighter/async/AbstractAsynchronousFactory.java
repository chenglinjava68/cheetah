package org.cheetah.fighter.async;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.cheetah.fighter.worker.WorkerFactory;

import java.util.concurrent.*;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;

/**
 * Created by Max on 2016/5/3.
 */
public abstract class AbstractAsynchronousFactory<T> implements AsynchronousFactory<T> {
    private int minThreads = Runtime.getRuntime().availableProcessors() * 2 + 16;
    private int maxThreads = Runtime.getRuntime().availableProcessors() * 2 + 16;
    private int queueLength = 100000;
    private RejectedExecutionHandler rejectedExecutionHandler = new AbortPolicy();
    private ExecutorService executorService;
    private WorkerFactory workerFactory;

    @Override
    public void start() {
        buildExecutorService();
    }

    @Override
    public void stop() {
        if (executorService != null) {
            while (!executorService.isShutdown()) {
                executorService.shutdown();
            }

            executorService = null;
        }
    }

    public int minThreads() {
        return minThreads;
    }

    public void setMinThreads(int minThreads) {
        this.minThreads = minThreads;
    }

    public int maxThreads() {
        return maxThreads;
    }

    public void setMaxThreads(int maxThreads) {
        this.maxThreads = maxThreads;
    }

    protected synchronized ExecutorService buildExecutorService() {
        if(this.executorService == null)
            executorService =  new ThreadPoolExecutor(minThreads, maxThreads,
                    3000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(queueLength),
                    new ThreadFactoryBuilder().setNameFormat("Cheetah-Fighter-%d").build(), rejectedExecutionHandler);
        return executorService;
    }

    public void setQueueLength(int queueLength) {
        this.queueLength = queueLength;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
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
    }
}
