package cheetah.domain;

import cheetah.util.UUIDGenerator;

/**
 * �ṩUUID�������ɹ���ĵ�ʵ�����
 * Created by Max on 2015/12/25.
 */
public class UUIDKeyEntity extends AbstractEntity<UUIDTrackingId> {

    public UUIDKeyEntity() {
        super(new UUIDTrackingId(UUIDGenerator.uuid()));
    }

    public UUIDKeyEntity(UUIDTrackingId trackingId) {
        super(trackingId);
    }

    public UUIDKeyEntity(EntityBuilder builder) {
        super((UUIDTrackingId) builder.trackingId, builder.timist, builder.version);
    }
}
