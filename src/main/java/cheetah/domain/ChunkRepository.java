package cheetah.domain;

import cheetah.domain.jpa.JpaCallback;

import java.util.List;

/**
 * Created by Max on 2016/1/9.
 */
public interface ChunkRepository<I extends TrackingId, T extends AbstractEntity<I>> {
    List<T> list();

    List<T> list(AmpleQuerier querier);

    List<T> list(AmpleQuerier querier, JpaCallback<List<T>> callback);

    long count(AmpleQuerier ampleQuerier);

}
