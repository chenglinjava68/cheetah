package jvddd.domain;

/**
 * �ṩ�Զ��������������ʵ�����
 * Created by Max on 2015/12/25.
 */
public class NumberKeyEntity extends AbstractEntity<NumberTrackingId> {
    public NumberKeyEntity() {
        super(new NumberTrackingId());
    }

    public NumberKeyEntity(NumberTrackingId trackingId) {
        super(trackingId);
    }

    public NumberKeyEntity(EntityBuilder builder) {
        super((NumberTrackingId) builder.trackingId, builder.timist, builder.version);
    }
}
