package cheetah.domain;


/**
 * Created by Max on 2016/1/6.
 */
public interface PagingRepository<I extends TrackingId, T extends AbstractEntity<I>> {
    Page<T> find(PageRequest request);

//    Page<T> find(PageRequest request, JpaCallback<Page<T>> callback);

}
