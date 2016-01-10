package cheetah.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Max on 2016/1/6.
 */
public class PageRequest extends AbstractPageable  implements Querier {
    private final Querier querier = new QuerierImpl();

    public PageRequest(int page, int size) {
        super(page, size);
    }

    @Override
    public final OrderList orderList() {
        return querier.orderList();
    }

    @Override
    public final void orderby(String property, Order.Direction order) {
        querier.orderby(property, order);
    }

    @Override
    public final void and(String name, Object value) {
        querier.and(name, value);
    }

    @Override
    public final void or(String name, Object value) {
        querier.or(name, value);
    }

    @Override
    public final void like(String name, String value) {
        querier.like(name, value);
    }

    @Override
    public void in(String property, List<Object> params) {
        querier.in(property, params);
    }

    @Override
    public void notIn(String property, List<Object> params) {
        querier.notIn(property, params);
    }

    @Override
    public void isNull(String property) {
        querier.isNull(property);
    }

    @Override
    public void notNull(String property) {
        querier.notNull(property);
    }

    @Override
    public void between(String property, Object start, Object end) {
        querier.between(property, start, end);
    }

    @Override
    public void gt(String property, Object value) {
        querier.gt(property, value);
    }

    @Override
    public void lt(String property, Object value) {
        querier.lt(property, value);
    }

    @Override
    public void ge(String property, Object value) {
        querier.ge(property, value);
    }

    @Override
    public void le(String property, Object value) {
        querier.le(property, value);
    }

    @Override
    public HashMap<String, Object> gt() {
        return querier.gt();
    }

    @Override
    public Map<String, Object> lt() {
        return querier.lt();
    }

    @Override
    public Map<String, Object> ge() {
        return querier.ge();
    }

    @Override
    public Map<String, Object> le() {
        return querier.le();
    }

    @Override
    public void clearAll() {
        querier.clearAll();
    }

    public final Map<String, String> like() {
        return querier.like();
    }

    public final Map<String, Object> or() {
        return querier.or();
    }

    public final Map<String, Object> and() {
        return querier.and();
    }

    @Override
    public int getNextPage() {
        return getPageNo() + 1;
    }

    @Override
    public int getPrePage() {
        return hasPrevious() ? getNextPage() - 1 : first();
    }

    @Override
    public int first() {
        return 0;
    }
}
