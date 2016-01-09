package cheetah.domain;

import java.util.List;

/**
 * Created by Max on 2016/1/9.
 */
public interface ChunkRepository<I extends TrackingId, T extends AbstractEntity<I>> {
    List<T> list();

    List<T> list(Querier querier);
}
