package jvddd.domain;

/**
 * 实现自动增长主键规则
 * Created by Max on 2015/12/25.
 */
public class NumberTrackingId extends TrackingId<Long> {
    @Override
    public boolean sameAs(TrackingId other) {
        return other != null && this.id == other.id;
    }
}
