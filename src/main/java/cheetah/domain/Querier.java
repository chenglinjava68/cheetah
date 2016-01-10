package cheetah.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 条件接口
 * Created by Max on 2016/1/9.
 */
public interface Querier {

    void orderby(String property, Order.Direction order);

    void and(String name, Object value);

    void or(String name, Object value);

    void like(String name, String value);

    void in(String property, List<Object> params);

    void notIn(String property, List<Object> params);

    void isNull(String property);

    void notNull(String property);

    void between(String property, Object start, Object end);

    void gt(String property, Object value);

    void lt(String property, Object value);

    void ge(String property, Object value);

    void le(String property, Object value);

    HashMap<String, Object> gt();

    Map<String, Object> lt();

    Map<String, Object> ge();

    Map<String, Object> le();

    Map<String, String> like();

    Map<String, Object> or();

    Map<String, Object> and();

    OrderList orderList();

    void clearAll();
}
