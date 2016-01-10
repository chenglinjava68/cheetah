package cheetah.domain.hibernate;

import cheetah.domain.AmpleQuerier;
import cheetah.domain.PageRequest;
import cheetah.domain.Querier;
import cheetah.domain.QueryInjector;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by Max on 2016/1/10.
 */
public interface HibernateQueryInjector extends QueryInjector {
    <R extends CriteriaQuery, T> void where(Querier querier, CriteriaBuilder criteriaBuilder, R criteriaQuery, Root<T> from);

    <T> void and(Querier querier, CriteriaBuilder criteriaBuilder, Root<T> from, List<Predicate> predicates);

    <T> void or(Querier querier, CriteriaBuilder criteriaBuilder, Root<T> from, List<Predicate> predicates);

    <T> void in(Querier querier, CriteriaBuilder criteriaBuilder, Root<T> from, List<Predicate> predicates);

    <T> void notIn(Querier querier, CriteriaBuilder criteriaBuilder, Root<T> from, List<Predicate> predicates);

    <T> void gt(Querier querier, CriteriaBuilder criteriaBuilder, Root<T> from, List<Predicate> predicates);

    <T> void lt(Querier querier, CriteriaBuilder criteriaBuilder, Root<T> from, List<Predicate> predicates);

    <T> void ge(Querier querier, CriteriaBuilder criteriaBuilder, Root<T> from, List<Predicate> predicates);

    <T> void le(Querier querier, CriteriaBuilder criteriaBuilder, Root<T> from, List<Predicate> predicates);

    <T> void between(Querier querier, CriteriaBuilder criteriaBuilder, Root<T> from, List<Predicate> predicates);

    <T> void like(Querier querier, CriteriaBuilder criteriaBuilder, Root<T> from, List<Predicate> predicates);

    <T> void limit(TypedQuery<T> tTypedQuery, PageRequest request);

    <T> void orderby(Querier querier, CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, Root<T> from);

    <T> void groupby(AmpleQuerier querier, CriteriaQuery<?> criteriaQuery, Root<T> from);
}
