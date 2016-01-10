package cheetah.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Max on 2016/1/6.
 */
public class QuerierImpl implements AmpleQuerier {

    private final OrderList orderby = new OrderList();
    private final Map<String, Object> and = new HashMap<String, Object>();
    private final Map<String, Object> or = new HashMap<String, Object>();
    private final Map<String, String> like = new HashMap<String, String>();
    private final Map<String, List<Object>> in = new HashMap<String, List<Object>>();
    private final Map<String, List<Object>> notIn = new HashMap<String, List<Object>>();
    private final Map<String, Object> gt = new HashMap<String, Object>();
    private final Map<String, Object> lt = new HashMap<String, Object>();
    private final Map<String, Object> ge = new HashMap<String, Object>();
    private final Map<String, Object> le = new HashMap<String, Object>();
    private String isNull;
    private String notNull;
    private Between between;
    private String groupby;

    @Override
    public final OrderList orderList() {
        return orderby;
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
    public final  void like(String name, String value) {
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
    public void between(String property, Object start, Object end) {
        this.between = new Between(property, start, end);
    }

    @Override
    public void gt(String property, Object value) {
        gt.put(property, value);
    }

    @Override
    public void lt(String property, Object value) {
        gt.put(property, value);
    }

    @Override
    public void ge(String property, Object value) {
        ge.put(property, value);
    }

    @Override
    public void le(String property, Object value) {
        le.put(property, value);
    }

    @Override
    public HashMap<String, Object> gt() {
        return new HashMap<String, Object>(gt);
    }

    @Override
    public Map<String, Object> lt() {
        return new HashMap<String, Object>(lt);
    }

    @Override
    public Map<String, Object> ge() {
        return new HashMap<String, Object>(ge);
    }

    @Override
    public Map<String, Object> le() {
        return new HashMap<String, Object>(le);
    }

    @Override
    public final Map<String, String> like() {
        return new HashMap<String, String>(like);
    }

    @Override
    public final Map<String, Object> or() {
        return new HashMap<String, Object>(or);
    }

    @Override
    public final Map<String, Object> and() {
        return new HashMap<String, Object>(and);
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

    public static class Between {
        private String name;
        private Object start;
        private Object end;

        public Between(String name, Object start, Object end) {
            this.name = name;
            this.start = start;
            this.end = end;
        }

        public String getName() {
            return name;
        }

        public Object getStart() {
            return start;
        }

        public Object getEnd() {
            return end;
        }
    }

}
