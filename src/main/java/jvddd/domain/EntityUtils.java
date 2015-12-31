package jvddd.domain;

/**
 * Created by Max on 2015/12/31.
 */
public class EntityUtils {

    public static UUIDTrackingId createTrackingId(String trackingId) {
        return new UUIDTrackingId(trackingId);
    }

    public static NumberTrackingId createTrackingId(Long trackingId) {
        return new NumberTrackingId(trackingId);
    }
}
