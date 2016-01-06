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
        long total = executeCount(request, criteriaBuilder);
        List<T> result = executeQuery(request, criteriaBuilder);
        return Page.create(total, result, request.getPageSize(), request.getNextPage());
    }

    private List<T> executeQuery(PageRequest request, CriteriaBuilder criteriaBuilder) {
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(getEntityClass());
        Root<T> from = criteriaQuery.from(this.getEntityClass());
        QueryHelper.where(request, criteriaBuilder, criteriaQuery, from);
        TypedQuery<T> tTypedQuery = entityManager.createQuery(criteriaQuery);
        QueryHelper.limit(tTypedQuery, request);
        return tTypedQuery.getResultList();
    }

    private long executeCount(PageRequest request, CriteriaBuilder criteriaBuilder) {
        CriteriaQuery<Long> ccQuery = criteriaBuilder.createQuery(Long.class);
        Root<T> from = ccQuery.from(this.getEntityClass());
        ccQuery.select(criteriaBuilder.count(from));
        QueryHelper.where(request, criteriaBuilder, ccQuery, from);
        TypedQuery<Long> cQuery = entityManager.createQuery(ccQuery);
        return cQuery.getSingleResult();
    }


}
