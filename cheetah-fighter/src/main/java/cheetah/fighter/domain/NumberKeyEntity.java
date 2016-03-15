package cheetah.fighter.domain;

/**
 * 基于数字类型id的模型基类
 * Created by Max on 2015/12/25.
 */
public abstract class NumberKeyEntity extends AbstractEntity<NumberTrackingId> {
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
