package cheetah.domain;

import java.util.List;
import java.util.Map;

/**
 * Created by Max on 2016/1/9.
 */
public interface Enquirer {
    public static enum CompositeType {
        AND,
        OR
    }
    boolean hasWhere();

    void orderby(String property, Order.Direction order);

    void and(String name, Object value);

    void or(String name, Object value);

    void like(String name, String value);

    void in(String property, List<Object> params);

    void notIn(String property, List<Object> params);

    void isNull(String property);

    void notNull(String property);

    void between(String property, Number start, Number end);

    void eq(String property, Object value);

    void gt(String property, Number value);

    void lt(String property, Number value);

    void ge(String property, Number value);

    void le(String property, Number value);

    Map<String, List<Object>> getIn();

    Map<String, List<Object>> getNotIn();

    String getIsNull();

    String getNotNull();

    Map<String, Number> getGt();

    Map<String, Number> getLt();

    Map<String, Number> getGe();

    Map<String, Number> getLe();

    Map<String, String> getLike();

    Map<String, Object> getOr();

    Map<String, Object> getAnd();

    EnquirerImpl.Between getBetween();

    OrderList getOrderList();

    void clearAll();
}
