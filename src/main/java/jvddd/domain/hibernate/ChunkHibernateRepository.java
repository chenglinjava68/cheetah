package jvddd.domain.hibernate;

import jvddd.domain.AbstractEntity;
import jvddd.domain.ChunkRepository;
import jvddd.domain.TrackingId;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

/**
 * Created by Max on 2016/1/9.
 */
public class ChunkHibernateRepository<I extends TrackingId, T extends AbstractEntity<I>> extends AbstractRepository<I, T> implements ChunkRepository<I, T> {

    @Override
    public List<T> list() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(getEntityClass());
        criteriaQuery.from(getEntityClass());
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
