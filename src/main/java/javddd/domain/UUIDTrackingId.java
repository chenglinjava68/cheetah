package javddd.domain;

import java.util.UUID;

/**
 * 实现UUID的主键生成规则
 * Created by Max on 2015/12/25.
 */
public class UUIDTrackingId extends TrackingId<String> {
    public UUIDTrackingId() {
        this.id = UUID.randomUUID().toString();
    }

    public UUIDTrackingId(String id) {
        super(id);
    }

    @Override
    public boolean sameAs(TrackingId other) {
        return other != null && this.id().equals(other.id());
    }
}
