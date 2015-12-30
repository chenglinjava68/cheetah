package javddd.domain;

/**
 * 提供UUID主键生成规则的的实体基类
 * Created by Max on 2015/12/25.
 */
public class UUIDKeyEntity extends AbstractEntity<String> {
    public UUIDKeyEntity() {
        super(new UUIDTrackingId());
    }

    @Override
    public TrackingId<String> id() {
        return this.trackingId;
    }
}
