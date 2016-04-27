package org.cheetah.domain;


import java.util.List;
import java.util.Map;

/**
 * @author Max
 * @email pdemok@163.com
 * @date 2015/3/21
 */
public interface Repository<T extends Entity, PK extends TrackingId> {
    T get(PK id);

    void put(T entity);

    int putAll(List<T> entities);

    void refresh(T entity);

    void refreshAll(List<T> entities);

    void remove(T entity);

    void removeAll(List<PK> ids);

    int size();

    int size(Map<String, Object> params);

    List<T> toList();

    List<T> toList(T entity);

    List<T> toList(String property, Object value);

    List<T> toList(Map<String, Object> params);

}
