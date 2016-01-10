package cheetah.domain.jpa;

import cheetah.domain.AmpleQuerier;
import cheetah.domain.PageRequest;
import cheetah.domain.Querier;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.*;

/**
 * Created by Max on 2016/1/10.
 */
public class JpaQueryAgreedImpl implements JpaQueryAgreed {

    @Override
    public <R extends CriteriaQuery, T> void where(Querier querier, CriteriaBuilder criteriaBuilder, R criteriaQuery, Root<T> from) {
        if(!querier.hasWhere()) return ;
        final List<Predicate> predicates = new ArrayList<Predicate>();
        and(querier, criteriaBuilder, from, predicates);
        or(querier, criteriaBuilder, from, predicates);
        in(querier, criteriaBuilder, from, predicates);
        notIn(querier, criteriaBuilder, from, predicates);
        gt(querier, criteriaBuilder, from, predicates);
        lt(querier, criteriaBuilder, from, predicates);
        ge(querier, criteriaBuilder, from, predicates);
        le(querier, criteriaBuilder, from, predicates);
        between(querier, criteriaBuilder, from, predicates);
        like(querier, criteriaBuilder, from, predicates);
        criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
    }

    @Override
    public <T> void and(Querier querier, CriteriaBuilder criteriaBuilder, Root<T> from, List<Predicate> predicates) {
        if (CollectionUtils.isEmpty(querier.getAnd())) return;
        final List<Predicate> predicateList = new ArrayList<Predicate>();
        for (Map.Entry<String, Object> entry : querier.getAnd().entrySet()) {
            Expression expression = QueryHelper.fieldProcessing(from, entry.getKey());
            predicateList.add(criteriaBuilder.equal(expression, entry.getValue()));
        }
        predicates.add(criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()])));
    }

    @Override
    public <T> void or(Querier querier, CriteriaBuilder criteriaBuilder, Root<T> from, List<Predicate> predicates) {
        if (CollectionUtils.isEmpty(querier.getOr())) return;
        final List<Predicate> predicateList = new ArrayList<Predicate>();
        for (Map.Entry<String, Object> entry : querier.getOr().entrySet()) {
            Expression expression = QueryHelper.fieldProcessing(from, entry.getKey());
            predicateList.add(criteriaBuilder.equal(expression, entry.getValue()));
        }
        predicates.add(criteriaBuilder.or(predicateList.toArray(new Predicate[predicates.size()])));
    }

    @Override
    public <T> void in(Querier querier, CriteriaBuilder criteriaBuilder, Root<T> from, List<Predicate> predicates) {
        if (CollectionUtils.isEmpty(querier.in())) return;
        for (Map.Entry<String, List<Object>> entry : querier.in().entrySet()) {
            Expression expression = QueryHelper.fieldProcessing(from, entry.getKey());
            CriteriaBuilder.In in = criteriaBuilder.in(expression);
            Iterator<Object> iter = entry.getValue().iterator();
            while (iter.hasNext()) {
                in.value(iter.next());
            }
            predicates.add(in);
        }
    }

    @Override
    public <T> void notIn(Querier querier, CriteriaBuilder criteriaBuilder, Root<T> from, List<Predicate> predicates) {
        if (CollectionUtils.isEmpty(querier.notIn())) return;
        for (Map.Entry<String, List<Object>> entry : querier.notIn().entrySet()) {
            Expression expression = QueryHelper.fieldProcessing(from, entry.getKey());
            CriteriaBuilder.In in = criteriaBuilder.in(expression);
            Iterator<Object> iter = entry.getValue().iterator();
            while (iter.hasNext()) {
                in.value(iter.next());
            }
            predicates.add(criteriaBuilder.not(in));
        }

    }

    @Override
    public <T> void gt(Querier querier, CriteriaBuilder criteriaBuilder, Root<T> from, List<Predicate> predicates) {
        if (CollectionUtils.isEmpty(querier.getGt())) return;
        final List<Predicate> predicateList = new ArrayList<Predicate>();
        for (Map.Entry<String, Number> entry : querier.getGt().entrySet()) {
            Expression expression = QueryHelper.fieldProcessing(from, entry.getKey());
            predicateList.add(criteriaBuilder.gt(expression, entry.getValue()));
        }
        predicates.addAll(predicateList);
    }

    @Override
    public <T> void lt(Querier querier, CriteriaBuilder criteriaBuilder, Root<T> from, List<Predicate> predicates) {
        if (CollectionUtils.isEmpty(querier.getLt())) return;
        final List<Predicate> predicateList = new ArrayList<Predicate>();
        for (Map.Entry<String, Number> entry : querier.getLt().entrySet()) {
            Expression expression = QueryHelper.fieldProcessing(from, entry.getKey());
            predicateList.add(criteriaBuilder.lt(expression, entry.getValue()));
        }
        predicates.addAll(predicateList);
    }

    @Override
    public <T> void ge(Querier querier, CriteriaBuilder criteriaBuilder, Root<T> from, List<Predicate> predicates) {
        if (CollectionUtils.isEmpty(querier.getGe())) return;
        final List<Predicate> predicateList = new ArrayList<Predicate>();
        for (Map.Entry<String, Number> entry : querier.getGe().entrySet()) {
            Expression expression = QueryHelper.fieldProcessing(from, entry.getKey());
            predicateList.add(criteriaBuilder.ge(expression, entry.getValue()));
        }
        predicates.addAll(predicateList);
    }

    @Override
    public <T> void le(Querier querier, CriteriaBuilder criteriaBuilder, Root<T> from, List<Predicate> predicates) {
        if (CollectionUtils.isEmpty(querier.getLe())) return;
        final List<Predicate> predicateList = new ArrayList<Predicate>();
        for (Map.Entry<String, Number> entry : querier.getLe().entrySet()) {
            Expression expression = QueryHelper.fieldProcessing(from, entry.getKey());
            predicateList.add(criteriaBuilder.le(expression, entry.getValue()));
        }
        predicates.addAll(predicateList);
    }

    @Override
    public <T> void between(Querier querier, CriteriaBuilder criteriaBuilder, Root<T> from, List<Predicate> predicates) {
        if (Objects.isNull(querier.getBetween())) return;
        Expression expression = QueryHelper.fieldProcessing(from, querier.getBetween().getName());
        if (querier.getBetween().getStart() instanceof String)
            predicates.add(criteriaBuilder.between(expression, (String) querier.getBetween().getStart(), (String) querier.getBetween().getEnd()));
        else if (querier.getBetween().getStart() instanceof Long)
            predicates.add(criteriaBuilder.between(expression, (Long) querier.getBetween().getStart(), (Long) querier.getBetween().getEnd()));
        else if (querier.getBetween().getStart() instanceof Integer)
            predicates.add(criteriaBuilder.between(expression, (Integer) querier.getBetween().getStart(), (Integer) querier.getBetween().getEnd()));
        else if (querier.getBetween().getStart() instanceof Short)
            predicates.add(criteriaBuilder.between(expression, (Short) querier.getBetween().getStart(), (Short) querier.getBetween().getEnd()));
        else if (querier.getBetween().getStart() instanceof Float)
            predicates.add(criteriaBuilder.between(expression, (Float) querier.getBetween().getStart(), (Float) querier.getBetween().getEnd()));
        else if (querier.getBetween().getStart() instanceof Double)
            predicates.add(criteriaBuilder.between(expression, (Double) querier.getBetween().getStart(), (Double) querier.getBetween().getEnd()));
    }

    @Override
    public <T> void like(Querier querier, CriteriaBuilder criteriaBuilder, Root<T> from, List<Predicate> predicates) {
        if (CollectionUtils.isEmpty(querier.getLike())) return;
        for (Map.Entry<String, String> entry : querier.getLike().entrySet()) {
            Expression expression = QueryHelper.fieldProcessing(from, entry.getKey());
            predicates.add(criteriaBuilder.like(expression, String.format("%%%s%%", entry.getValue())));
        }
    }

    @Override
    public <T> void limit(TypedQuery<T> tTypedQuery, PageRequest request) {
        tTypedQuery.setFirstResult(request.getOffset()).setMaxResults(request.getPageSize());
    }

    @Override
    public <T> void orderby(Querier querier, CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, Root<T> from) {
        if (Objects.isNull(querier.orderList()) || querier.orderList().isEmpty())
            return;
        List<Order> orders = new ArrayList<Order>();
        for (cheetah.domain.Order order : querier.orderList().orders()) {
            if (order.getDirection() == cheetah.domain.Order.Direction.DESC)
                orders.add(criteriaBuilder.desc(from.get(order.getproperty())));
            else
                orders.add(criteriaBuilder.asc(from.get(order.getproperty())));
        }
        criteriaQuery.orderBy(orders);
    }

    @Override
    public <T> void groupby(AmpleQuerier querier, CriteriaQuery<?> criteriaQuery, Root<T> from) {
        if (StringUtils.isBlank(querier.groupby())) return;
        criteriaQuery.groupBy(from.get(querier.groupby()));
    }
}
