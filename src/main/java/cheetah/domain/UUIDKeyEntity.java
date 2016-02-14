package cheetah.domain;

import cheetah.util.IDGenerator;

/**
 * UUID模型类型
 * Created by Max on 2015/12/25.
 */
public abstract class UUIDKeyEntity extends AbstractEntity<UUIDTrackingId> {

    public UUIDKeyEntity() {
        super(new UUIDTrackingId(IDGenerator.generateId()));
    }

    public UUIDKeyEntity(UUIDTrackingId trackingId) {
        super(trackingId);
    }

    public UUIDKeyEntity(EntityBuilder builder) {
        super((UUIDTrackingId) builder.trackingId, builder.timist, builder.version);
    }
}
