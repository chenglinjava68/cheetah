package jvddd.domain;

import jvddd.domain.hibernate.FromHelper;
import org.springframework.util.CollectionUtils;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Max on 2015/12/31.
 */
public final class QueryHelper {
    public static UUIDTrackingId createQueryTrackingId(String trackingId) {
        return EntityUtils.createTrackingId(trackingId);
    }

    public static NumberTrackingId createTrackingId(Long trackingId) {
        return EntityUtils.createTrackingId(trackingId);
    }

    public static <R extends CriteriaQuery, T> void where(PageRequest request, CriteriaBuilder criteriaBuilder, R criteriaQuery, Root<T> from) {
        final List<Predicate> predicates = new ArrayList<Predicate>();
        and(request, criteriaBuilder, from, predicates);
        or(request, criteriaBuilder, from, predicates);
        like(request, criteriaBuilder, from, predicates);
        criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
    }

    public static <T> void and(PageRequest request, CriteriaBuilder criteriaBuilder, Root<T> from, List<Predicate> predicates) {
        if (CollectionUtils.isEmpty(request.getAndParameters())) return;
        final List<Predicate> predicateList = new ArrayList<Predicate>();
        for (Map.Entry<String, Object> entry : request.getAndParameters().entrySet()) {
            Expression expression = fieldProcessing(from, entry);
            predicateList.add(criteriaBuilder.equal(expression, entry.getValue()));
        }
        predicates.add(criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()])));
    }

    private static <T> Expression fieldProcessing(Root<T> from, Map.Entry<String, ?> entry) {
        Expression expression = null;
        if (entry.getKey().contains(".")) {
            String[] keys = entry.getKey().split("\\.");
            expression = doFieldProcessing(keys, from);
        } else
            expression = from.get(entry.getKey());
        return expression;
    }


    public static <T> void or(PageRequest request, CriteriaBuilder criteriaBuilder, Root<T> from, List<Predicate> predicates) {
        if (CollectionUtils.isEmpty(request.getOrParameters())) return;
        final List<Predicate> predicateList = new ArrayList<Predicate>();
        for (Map.Entry<String, Object> entry : request.getOrParameters().entrySet()) {
            Expression expression = fieldProcessing(from, entry);
            predicateList.add(criteriaBuilder.equal(expression, entry.getValue()));
        }
        predicates.add(criteriaBuilder.or(predicateList.toArray(new Predicate[predicates.size()])));
    }

    public static <T> void like(PageRequest request, CriteriaBuilder criteriaBuilder, Root<T> from, List<Predicate> predicates) {
        if (CollectionUtils.isEmpty(request.getLikeParameters())) return;
        for (Map.Entry<String, String> entry : request.getLikeParameters().entrySet()) {
            Expression expression = fieldProcessing(from, entry);
            predicates.add(criteriaBuilder.like(expression, String.format("%%%s%%", entry.getValue())));
        }
    }

    public static <T> void limit(TypedQuery<T> tTypedQuery, PageRequest request) {
        tTypedQuery.setFirstResult(request.getOffset()).setMaxResults(request.getPageSize());
    }

    private static <T> Expression doFieldProcessing(String[] keys, Root<T> from) {
        switch (keys.length) {
            case 2:
                return FromHelper.get(keys[0], keys[1], from);
            case 3:
                return FromHelper.get(keys[0], keys[1], keys[2], from);
            case 4:
                return FromHelper.get(keys[0], keys[1], keys[2], keys[3], from);
            case 5:
                return FromHelper.get(keys[0], keys[1], keys[2], keys[3], keys[4], from);
            case 6:
                return FromHelper.get(keys[0], keys[1], keys[2], keys[3], keys[4], keys[5], from);
            case 7:
                return FromHelper.get(keys[0], keys[1], keys[2], keys[3], keys[4], keys[5], keys[6], from);
            case 8:
                return FromHelper.get(keys[0], keys[1], keys[2], keys[3], keys[4], keys[5], keys[6], keys[7], from);
            default:
                throw new RuntimeException("doFieldProcessing failure");
        }
    }
}
