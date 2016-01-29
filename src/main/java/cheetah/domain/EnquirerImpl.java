package cheetah.domain;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Max on 2016/1/6.
 */
public class EnquirerImpl implements AmpleEnquirer {

    private final OrderList orderby = new OrderList();
    private final Map<String, Object> and = new HashMap<String, Object>();
    private final Map<String, Object> or = new HashMap<String, Object>();
    private final Map<String, String> like = new HashMap<String, String>();
    private final Map<String, List<Object>> in = new HashMap<String, List<Object>>();
    private final Map<String, List<Object>> notIn = new HashMap<String, List<Object>>();
    private final Map<String, Number> gt = new HashMap<String, Number>();
    private final Map<String, Number> lt = new HashMap<String, Number>();
    private final Map<String, Number> ge = new HashMap<String, Number>();
    private final Map<String, Number> le = new HashMap<String, Number>();
    private String isNull;
    private String notNull;
    private Between between;
    private String groupby;

    @Override
    public final OrderList getOrderList() {
        return orderby;
    }

    @Override
    public boolean hasWhere() {
        return !and.isEmpty() || !or.isEmpty() || !like.isEmpty() || !in.isEmpty() || !notIn.isEmpty() ||
                !gt.isEmpty() || !lt.isEmpty() || !ge.isEmpty() || !le.isEmpty() || !StringUtils.isBlank(isNull) ||
                !StringUtils.isBlank(notNull) || !Objects.isNull(between);
    }

    @Override
    public final void orderby(String property, Order.Direction order) {
        orderby.add(property, order);
    }

    @Override
    public final void and(String name, Object value) {
        and.put(name, value);
    }

    @Override
    public final void or(String name, Object value) {
        or.put(name, value);
    }

    @Override
    public final void like(String name, String value) {
        this.like.put(name, value);
    }

    @Override
    public void in(String property, List<Object> params) {
        this.in.put(property, params);
    }

    @Override
    public void notIn(String property, List<Object> params) {
        this.notIn.put(property, params);
    }

    @Override
    public void isNull(String property) {
        this.isNull = property;
    }

    @Override
    public void notNull(String property) {
        this.notNull = property;
    }

    @Override
    public void between(String property, Number start, Number end) {
        this.between = new Between<Number>(property, start, end);
    }

    @Override
    public void eq(String property, Object value) {

    }

    @Override
    public void gt(String property, Number value) {
        gt.put(property, value);
    }

    @Override
    public void lt(String property, Number value) {
        lt.put(property, value);
    }

    @Override
    public void ge(String property, Number value) {
        ge.put(property, value);
    }

    @Override
    public void le(String property, Number value) {
        le.put(property, value);
    }

    @Override
    public Map<String, List<Object>> getIn() {
        return new HashMap<String, List<Object>>(in);
    }

    @Override
    public Map<String, List<Object>> getNotIn() {
        return new HashMap<String, List<Object>>(notIn);
    }

    @Override
    public String getIsNull() {
        return isNull;
    }

    @Override
    public String getNotNull() {
        return notNull;
    }

    @Override
    public Map<String, Number> getGt() {
        return new HashMap<String, Number>(gt);
    }

    @Override
    public Map<String, Number> getLt() {
        return new HashMap<String, Number>(lt);
    }

    @Override
    public Map<String, Number> getGe() {
        return new HashMap<String, Number>(ge);
    }

    @Override
    public Map<String, Number> getLe() {
        return new HashMap<String, Number>(le);
    }

    @Override
    public final Map<String, String> getLike() {
        return new HashMap<String, String>(like);
    }

    @Override
    public final Map<String, Object> getOr() {
        return new HashMap<String, Object>(or);
    }

    @Override
    public final Map<String, Object> getAnd() {
        return new HashMap<String, Object>(and);
    }

    @Override
    public Between getBetween() {
        return between;
    }

    @Override
    public String groupby() {
        return groupby;
    }

    @Override
    public void groupby(String name) {
        this.groupby = name;
    }

    @Override
    public final void clearAll() {
        this.orderby.clear();
        this.and.clear();
        this.or.clear();
        this.like.clear();
        this.in.clear();
        this.notIn.clear();
        this.gt.clear();
        this.lt.clear();
        this.ge.clear();
        this.le.clear();
        this.groupby = null;
        this.isNull = null;
        this.notNull = null;
        this.between = null;
    }

    public static class Between<T> {
        private String name;
        private T start;
        private T end;


        public Between(String name, T start, T end) {
            this.name = name;
            this.start = start;
            this.end = end;
        }

        public String getName() {
            return name;
        }

        public T getStart() {
            return start;
        }

        public T getEnd() {
            return end;
        }
    }

}
