package org.cheetah.bootstraps;

import org.cheetah.commons.logger.Loggers;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.CountDownLatch;

/**
 * @author siuming
 */
public class SpringBootstrap implements Bootstrap {

    private final String[] configLocations;
    private ClassPathXmlApplicationContext applicationContext;

    public SpringBootstrap(String... configLocations) {
        this.configLocations = configLocations;
    }

    @Override
    public void bootstrap() {

        Loggers.me().warn(getClass(), "start bootstrap...");
        start();
        final CountDownLatch signal = new CountDownLatch(1);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                signal.countDown();
                SpringBootstrap.this.stop();
            }
        });

        new Thread() {
            @Override
            public void run() {
                try {
                    signal.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    Loggers.me().error(getClass(), "signal await occurs error.", e);
                }
            }
        }.start();
        Loggers.me().warn(getClass(), "start bootstrap finished.");
    }

    private void start() {
        applicationContext = new ClassPathXmlApplicationContext(configLocations);
        applicationContext.start();
    }

    private void stop() {
        applicationContext.stop();
        applicationContext.close();
    }
}
