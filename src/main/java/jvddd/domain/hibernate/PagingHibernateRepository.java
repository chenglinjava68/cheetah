package jvddd.domain.hibernate;

import jvddd.domain.*;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by Max on 2016/1/5.
 */
public abstract class PagingHibernateRepository<I extends TrackingId, T extends AbstractEntity<I>>
        extends AbstractHibernateRepository<I, T> {
    public Page<T> find(PageRequest request) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Long> ccQuery = criteriaBuilder.createQuery(Long.class);
        Root<T> cfrom = ccQuery.from(this.getEntityClass());
        ccQuery.select(criteriaBuilder.count(cfrom));
        QueryHelper.where(request, criteriaBuilder, ccQuery, cfrom);
        TypedQuery<Long> cQuery = entityManager.createQuery(ccQuery);
        long total = cQuery.getSingleResult();

        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(getEntityClass());
        Root<T> bfrom = criteriaQuery.from(this.getEntityClass());
        QueryHelper.where(request, criteriaBuilder, criteriaQuery, bfrom);
        TypedQuery<T> tTypedQuery = entityManager.createQuery(criteriaQuery);
        QueryHelper.limit(tTypedQuery, request);
        List<T> result = tTypedQuery.getResultList();
        return Page.create(total, result, request.getPageSize(), request.getNextPage());
    }
}
