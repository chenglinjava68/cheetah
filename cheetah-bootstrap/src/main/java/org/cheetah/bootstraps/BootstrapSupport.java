package org.cheetah.bootstraps;

import org.cheetah.commons.logger.Err;
import org.cheetah.commons.logger.Warn;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Max on 2016/5/22.
 */
public abstract class BootstrapSupport implements Bootstrap {
    @Override
    public void bootstrap() {
        try {
            final CountDownLatch signal = new CountDownLatch(1);
            Runnable recycle = new Runnable() {
                @Override
                public void run() {
                    {
                        signal.countDown();
                        BootstrapSupport.this.shutdown();
                    }
                }
            };
            Runtime.getRuntime().addShutdownHook(new Thread(recycle));
            Warn.log(this.getClass(), "bootstrap starting ...");
            this.startup();
            Runnable guard = new Runnable() {
                @Override
                public void run() {
                    try {
                        signal.await();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        Err.log(this.getClass(), "signal await occurs error.", e);
                    }
                }
            };
            (new Thread(guard)).start();
            Warn.log(this.getClass(), "bootstrap finished.");
        } catch (Exception e) {
            throw new BootstrapException("bootstrap occurs error.", e);
        }
    }

    protected abstract void startup() throws Exception;

    protected void shutdown() {
    }

}
