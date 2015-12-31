package jvddd.domain;


import javax.persistence.Column;

/**
 * 实现自动增长主键规则
 * Created by Max on 2015/12/25.
 */
public class NumberTrackingId extends TrackingId<Long> {
    @Column(name = "id")
    protected Long id;
    @Override
    public boolean sameAs(TrackingId other) {
        return other != null && this.id() == other.id();
    }

    @Override
    public Long id() {
        return id;
    }
}
