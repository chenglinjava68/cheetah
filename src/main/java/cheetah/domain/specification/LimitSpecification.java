package cheetah.domain.specification;

import cheetah.domain.AmpleEnquirer;
import cheetah.domain.Enquirer;
import cheetah.domain.PageRequest;
import cheetah.domain.QueryAgreement;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by Max on 2016/1/10.
 */
public interface LimitSpecification extends QueryAgreement {
    <R extends CriteriaQuery, T> void where(Enquirer enquirer, CriteriaBuilder criteriaBuilder, R criteriaQuery, Root<T> from);
//
//    <T> void and(Enquirer enquirer, CriteriaBuilder criteriaBuilder, Root<T> from, List<Predicate> predicates);
//
//    <T> void or(Enquirer enquirer, CriteriaBuilder criteriaBuilder, Root<T> from, List<Predicate> predicates);

    <T> void in(Enquirer enquirer, CriteriaBuilder criteriaBuilder, Root<T> from, List<Predicate> predicates);

    <T> void notIn(Enquirer enquirer, CriteriaBuilder criteriaBuilder, Root<T> from, List<Predicate> predicates);

    <T> void gt(Enquirer enquirer, CriteriaBuilder criteriaBuilder, Root<T> from, List<Predicate> predicates);

    <T> void lt(Enquirer enquirer, CriteriaBuilder criteriaBuilder, Root<T> from, List<Predicate> predicates);

    <T> void ge(Enquirer enquirer, CriteriaBuilder criteriaBuilder, Root<T> from, List<Predicate> predicates);

    <T> void le(Enquirer enquirer, CriteriaBuilder criteriaBuilder, Root<T> from, List<Predicate> predicates);

    <T> void between(Enquirer enquirer, CriteriaBuilder criteriaBuilder, Root<T> from, List<Predicate> predicates);

    <T> void like(Enquirer enquirer, CriteriaBuilder criteriaBuilder, Root<T> from, List<Predicate> predicates);

    <T> void limit(TypedQuery<T> tTypedQuery, PageRequest request);

    <T> void orderby(Enquirer enquirer, CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, Root<T> from);

    <T> void groupby(AmpleEnquirer enquirer, CriteriaQuery<?> criteriaQuery, Root<T> from);
}
