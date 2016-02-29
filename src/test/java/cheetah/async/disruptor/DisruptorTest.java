package cheetah.async.disruptor;

import cheetah.event.Event;
import cheetah.governor.support.AkkaGovernorTest;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Max on 2016/2/2.
 */
public class DisruptorTest {

    @Test
    public void test() {
        DisruptorEventFactory factory = new DisruptorEventFactory();
        ExecutorService executorService = Executors.newCachedThreadPool();
        int ringBufferSize = 1024 * 1024;

        Disruptor<DisruptorEvent> disruptor = new Disruptor<>(factory, ringBufferSize
                , executorService, ProducerType.SINGLE, new YieldingWaitStrategy());

        EventHandler<DisruptorEvent> eventHandler = new DisruptorEventHandler();
        disruptor.handleEventsWith(eventHandler);

        disruptor.start();

        CountDownLatch latch = new CountDownLatch(1);
        // 发布事件；
        RingBuffer<DisruptorEvent> ringBuffer = disruptor.getRingBuffer();

//        for (int i = 0; i < 100; i++)
//            new Thread(() -> {
//                while (true) {
//                    long sequence = ringBuffer.next();//请求下一个事件序号；
//                    try {
//                        DisruptorEvent event = ringBuffer.get(sequence);
//                        event.set(getEventData());
//                    } finally {
//                        ringBuffer.publish(sequence);
//                    }
//                }
//            }).start();

        Translator translator = new Translator();
        while (true) {
            Event data = getEventData();//获取要通过事件传递的业务数据；
            ringBuffer.publishEvent(translator, data);
        }
//        try {
//            latch.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    public Event getEventData() {
        return new AkkaGovernorTest.ApplicationEventTest("aaa");
    }

    static class Translator implements EventTranslatorOneArg<DisruptorEvent, Event> {
        @Override
        public void translateTo(DisruptorEvent event, long sequence, Event data) {
            event.set(data);
        }
    }
}
