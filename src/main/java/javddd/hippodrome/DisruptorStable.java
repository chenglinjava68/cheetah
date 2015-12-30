package javddd.hippodrome;

import com.lmax.disruptor.dsl.Disruptor;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Max on 2015/12/28.
 */
public class DisruptorStable implements Stable {
    private Disruptor disruptor;
    private ExecutorService executor = Executors.newCachedThreadPool();
    private String ringBufferSize = "10";

    public DisruptorStable(ExecutorService executor, String ringBufferSize) {
        this.executor = executor;
        this.ringBufferSize = ringBufferSize;
    }

    @Override
    public void start() {
        if(Objects.isNull(disruptor))
            newDisruptor();
        disruptor.start();
    }

    private void newDisruptor() {

    }

    @Override
    public void stop() {
        if(Objects.nonNull(disruptor))
            disruptor.shutdown();
        if(Objects.nonNull(executor))
            executor.shutdown();
        this.disruptor = null;
        this.ringBufferSize = null;
    }
}
