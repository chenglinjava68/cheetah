package org.cheetah.fighter.async;

import java.util.concurrent.*;

/**
 * Created by Max on 2016/5/3.
 */
public abstract class AbstractAsynchronousFactory<T> implements AsynchronousFactory<T> {
    private int minThreads = 256;
    private int maxThreads = 256;
    private ExecutorService executorService;

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
                    3000L, TimeUnit.MILLISECONDS, new LinkedTransferQueue());
        return executorService;
    }

    public ExecutorService executorService() {
        return executorService;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }
}
