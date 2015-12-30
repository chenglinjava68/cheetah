package javddd.domain;

/**
 * 提供自动增长主键规则的实体基类
 * Created by Max on 2015/12/25.
 */
public class IncrementKeyEntity extends AbstractEntity<Long> {
    public IncrementKeyEntity() {
        super(new NumberTrackingId());
    }

    @Override
    public TrackingId<Long> id() {
        return this.trackingId;
    }
}
