package jvddd.domain;

/**
 * �ṩ�Զ��������������ʵ�����
 * Created by Max on 2015/12/25.
 */
public class IncrementKeyEntity extends AbstractEntity<Long> {
    public IncrementKeyEntity() {
        super(new NumberTrackingId());
    }

}
