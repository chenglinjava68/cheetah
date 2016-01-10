package cheetah.domain.hibernate;

import cheetah.domain.*;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by Max on 2016/1/5.
 */
public class PagingHibernateRepository<I extends TrackingId, T extends AbstractEntity<I>>
        extends BasicHibernateRepository<I, T> implements PagingRepository<I, T> {
    private final HibernateQueryInjector queryInjector = new HibernateQueryInjectorImpl();

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
}
