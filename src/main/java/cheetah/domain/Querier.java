package cheetah.domain;

import java.util.List;
import java.util.Map;

/**
 * 条件接口
 * Created by Max on 2016/1/9.
 */
public interface Querier {

    boolean hasWhere();

    void orderby(String property, Order.Direction order);

    void getAnd(String name, Object value);

    void or(String name, Object value);

    void getLike(String name, String value);

    void in(String property, List<Object> params);

    void notIn(String property, List<Object> params);

    void isNull(String property);

    void notNull(String property);

    void between(String property, Number start, Number end);

    void gt(String property, Number value);

    void lt(String property, Number value);

    void ge(String property, Number value);

    void le(String property, Number value);

    Map<String, List<Object>> in();

    Map<String, List<Object>> notIn();

    String isNull();

    String notNull();

    Map<String, Number> getGt();

    Map<String, Number> getLt();

    Map<String, Number> getGe();

    Map<String, Number> getLe();

    Map<String, String> getLike();

    Map<String, Object> getOr();

    Map<String, Object> getAnd();

    QuerierImpl.Between getBetween();

    OrderList orderList();

    void clearAll();
}
