package jvddd.domain;

import java.util.List;

/**
 * Created by Max on 2015/12/31.
 */
public interface Repository<I extends TrackingId, T extends AbstractEntity<I>> {

    T get(I id);

    void put(T object);

    void refresh(T object);

    void refresh(I objectId);

    void remove(T object);

    void remove(I objectId);

    List<T> list();

    T getByPropertyValue(String propertyName, Object value);

}
