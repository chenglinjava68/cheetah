package jvddd.domain;

/**
 * �ṩUUID�������ɹ���ĵ�ʵ�����
 * Created by Max on 2015/12/25.
 */
public class UUIDKeyEntity extends AbstractEntity<String> {

    public UUIDKeyEntity() {
        super(new UUIDTrackingId());
    }

    public UUIDKeyEntity(TrackingId<String> trackingId, Timist timist, Long version) {
        super(trackingId, timist, version);
    }
}
