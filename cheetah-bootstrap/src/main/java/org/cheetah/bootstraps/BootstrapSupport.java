package org.cheetah.bootstraps;

import org.cheetah.commons.logger.Err;
import org.cheetah.commons.logger.Warn;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Max on 2016/5/22.
 */
public abstract class BootstrapSupport implements Bootstrap {
    private State state;

    public BootstrapSupport() {
        this.state = State.NEW;
    }

    @Override
    public void bootstrap() {
        if(state == State.STARTED)
            throw new BootstrapException("Service has been started, please don't repeat.");
        try {
            final CountDownLatch signal = new CountDownLatch(1);
            Runnable recycle = () -> {
                signal.countDown();
                BootstrapSupport.this.shutdown();
            };
            Runtime.getRuntime().addShutdownHook(new Thread(recycle));
            Warn.log(this.getClass(), "bootstrap starting ...");
            this.startup();
            this.state = State.STARTED;
            new Thread(() -> {
                try {
                    signal.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    Err.log(this.getClass(), "signal await occurs error.", e);
                }
            }).start();
            Warn.log(this.getClass(), "bootstrap finished.");
        } catch (Exception e) {
            throw new BootstrapException("bootstrap occurs error.", e);
        }
    }

    protected abstract void startup() throws Exception;

    protected void shutdown() {
        this.state = State.STOP;
    }

    enum State {
        NEW, STARTED, STOP
    }
}
