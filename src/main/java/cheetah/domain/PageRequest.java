package cheetah.domain;

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
    public boolean hasWhere() {
        return querier.hasWhere();
    }

    @Override
    public final void orderby(String property, Order.Direction order) {
        querier.orderby(property, order);
    }

    @Override
    public final void getAnd(String name, Object value) {
        querier.getAnd(name, value);
    }

    @Override
    public final void or(String name, Object value) {
        querier.or(name, value);
    }

    @Override
    public final void getLike(String name, String value) {
        querier.getLike(name, value);
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
    public void between(String property, Number start, Number end) {
        querier.between(property, start, end);
    }

    @Override
    public void gt(String property, Number value) {
        querier.gt(property, value);
    }

    @Override
    public void lt(String property, Number value) {
        querier.lt(property, value);
    }

    @Override
    public void ge(String property, Number value) {
        querier.ge(property, value);
    }

    @Override
    public void le(String property, Number value) {
        querier.le(property, value);
    }

    @Override
    public Map<String, List<Object>> in() {
        return querier.in();
    }

    @Override
    public Map<String, List<Object>> notIn() {
        return querier.notIn();
    }

    @Override
    public String isNull() {
        return querier.isNull();
    }

    @Override
    public String notNull() {
        return querier.notNull();
    }

    @Override
    public Map<String, Number> getGt() {
        return querier.getGt();
    }

    @Override
    public Map<String, Number> getLt() {
        return querier.getLt();
    }

    @Override
    public Map<String, Number> getGe() {
        return querier.getGe();
    }

    @Override
    public Map<String, Number> getLe() {
        return querier.getLe();
    }

    @Override
    public void clearAll() {
        querier.clearAll();
    }

    public final Map<String, String> getLike() {
        return querier.getLike();
    }

    public final Map<String, Object> getOr() {
        return querier.getOr();
    }

    public final Map<String, Object> getAnd() {
        return querier.getAnd();
    }

    @Override
    public QuerierImpl.Between getBetween() {
        return querier.getBetween();
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
