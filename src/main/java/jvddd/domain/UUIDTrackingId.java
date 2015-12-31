package jvddd.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * ʵ��UUID���������ɹ���
 * Created by Max on 2015/12/25.
 */
@Embeddable
public class UUIDTrackingId extends TrackingId<String> {
    @Column(name = "id")
    protected String id;
//    public UUIDTrackingId() {
//        this.id = UUID.randomUUID().toString();
//    }

//    public UUIDTrackingId(String id) {
//        super(id);
//    }

    @Override
    public boolean sameAs(TrackingId other) {
        return other != null && this.id().equals(other.id());
    }

    @Override
    public String id() {
        return id();
    }
}
