package cheetah.domain.jpa;

import cheetah.domain.*;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by Max on 2016/1/9.
 */
public class ChunkJpaRepository<I extends TrackingId, T extends AbstractEntity<I>> extends BasicJpaRepository<I, T> implements ChunkRepository<I, T> {
    private final JpaQueryAgreed queryInjector = new JpaQueryAgreedImpl();
    @Override
    public List<T> list() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(getEntityClass());
        criteriaQuery.from(getEntityClass());
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<T> list(AmpleEnquirer enquirer) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(getEntityClass());
        Root<T> bfrom = criteriaQuery.from(this.getEntityClass());

        queryInjector.where(enquirer, criteriaBuilder, criteriaQuery, bfrom);
        queryInjector.groupby(enquirer, criteriaQuery, bfrom);
        queryInjector.orderby(enquirer, criteriaBuilder, criteriaQuery, bfrom);
        TypedQuery<T> tTypedQuery = entityManager.createQuery(criteriaQuery);
        return tTypedQuery.getResultList();
    }

    @Override
    public List<T> list(AmpleEnquirer enquirer, JpaCallback<List<T>> callback) {
        return callback.doCallback(entityManager, enquirer);
    }

    @Override
    public long count(AmpleEnquirer enquirer) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> ccQuery = criteriaBuilder.createQuery(Long.class);
        Root<T> cfrom = ccQuery.from(this.getEntityClass());
        ccQuery.select(criteriaBuilder.count(cfrom));
        queryInjector.where(enquirer, criteriaBuilder, ccQuery, cfrom);
        TypedQuery<Long> cQuery = entityManager.createQuery(ccQuery);
        return cQuery.getSingleResult();
    }


}
