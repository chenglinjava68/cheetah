package org.cheetah.fighter.api;

import org.cheetah.fighter.DomainEvent;
import org.cheetah.fighter.EventCollector;
import org.cheetah.fighter.EventResult;
import org.cheetah.ioc.BeanFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by Max on 2016/1/10.
 */
public abstract class DomainEventPublisher {

    private static EventCollector collector = BeanFactory.getBean(EventCollector.class);

    private DomainEventPublisher() {
    }

    /**
     * ��Ϊ�򵥵ķ������޷�֪��ִ�н����disruptor��future��֧��
     * @param event
     * @param <E>
     */
    public static <E extends DomainEvent> void publish(E event) {
        collector.collect(event);
    }

    /**
     * ��������Եȴ��¼��������Ҫ��feedback��Ϊtrue�������Ҫ֪���¼�����ִ�еĽ����Ҫʹ��Future���棬
     * ���ʹ��disruptor,�õ���eventresult��һ���޷�֪�����������ֵ������feedback��timeout�����������
     * @param event
     * @param feedback  ��֧��future����
     * @return
     */
    public static <E extends DomainEvent> EventResult publish(E event, boolean feedback) {
        return collector.collect(event, feedback);
    }
    /**
     * ��������Եȴ��¼��������Ҫ��feedback��Ϊtrue������֧��������ִ�еĳ�ʱʱ�����ã������Ҫ֪���¼�
     * ����ִ�еĽ����Ҫʹ��Future���棬���ʹ��disruptor,�õ���eventresult��һ���޷�֪�����������ֵ��
     * ����feedback��timeout�����������
     * @param event
     * @param feedback  ��֧��future����
     * @param timeout   ��֧��future����
     * @return
     */
    public static <E extends DomainEvent> EventResult publish(E event, boolean feedback, int timeout) {
        return collector.collect(event, feedback, timeout);
    }
    /**
     * ��������Եȴ��¼��������Ҫ��feedback��Ϊtrue�������Ҫ֪���¼�����ִ�еĽ����Ҫʹ��Future���棬
     * ���ʹ��disruptor,�õ���eventresult��һ���޷�֪�����������ֵ������feedback��timeout�����������
     * @param event
     * @param feedback  ��֧��future����
     * @param timeout   ��֧��future����
     * @return
     */
    public static <E extends DomainEvent> EventResult publish(E event, boolean feedback, int timeout, TimeUnit timeUnit) {
        return collector.collect(event, feedback, timeout, timeUnit);
    }
}
