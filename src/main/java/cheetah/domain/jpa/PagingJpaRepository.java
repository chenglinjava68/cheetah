package cheetah.domain.jpa;

import cheetah.domain.*;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by Max on 2016/1/5.
 */
public class PagingJpaRepository<I extends TrackingId, T extends AbstractEntity<I>>
        extends BasicJpaRepository<I, T> implements PagingRepository<I, T> {
    private final JpaQueryAgreed queryInjector = new JpaQueryAgreedImpl();

    @Override
    public Page<T> find(PageRequest request) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Long> ccQuery = criteriaBuilder.createQuery(Long.class);
        Root<T> cfrom = ccQuery.from(this.getEntityClass());
        ccQuery.select(criteriaBuilder.count(cfrom));
        queryInjector.where(request, criteriaBuilder, ccQuery, cfrom);
        queryInjector.orderby(request, criteriaBuilder, ccQuery, cfrom);
        TypedQuery<Long> cQuery = entityManager.createQuery(ccQuery);
        long countTotal = cQuery.getSingleResult();

        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(getEntityClass());
        Root<T> bfrom = criteriaQuery.from(this.getEntityClass());
        queryInjector.where(request, criteriaBuilder, criteriaQuery, bfrom);
        queryInjector.orderby(request, criteriaBuilder, criteriaQuery, bfrom);
        TypedQuery<T> tTypedQuery = entityManager.createQuery(criteriaQuery);
        queryInjector.limit(tTypedQuery, request);
        List<T> result = tTypedQuery.getResultList();
        return Page.create(countTotal, result, request.getPageSize(), request.getNextPage());
    }

    @Override
    public Page<T> find(PageRequest request, JpaCallback<Page<T>> callback) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        return callback.doCallback(entityManager, request);
    }
}
