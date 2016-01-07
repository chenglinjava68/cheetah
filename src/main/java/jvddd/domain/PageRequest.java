package jvddd.domain;

import java.util.List;
import java.util.Map;

/**
 * Created by Max on 2016/1/6.
 */
public class PageRequest extends AbstractPageable {
    private final Querier querier = new Querier();

    public PageRequest(int page, int size) {
        super(page, size);
    }

    public final SortList sortList() {
        return querier.sortList();
    }

    public final PageRequest sort(List<String> properties, Sort.Order order) {
        querier.sort(properties, order);
        return this;
    }

    public final PageRequest and(String name, Object value) {
        querier.and(name, value);
        return this;
    }

    public final PageRequest or(String name, Object value) {
        querier.or(name, value);
        return this;
    }

    public final  PageRequest like(String name, String value) {
        querier.like(name, value);
        return this;
    }

    public final Map<String, String> getLikeParameters() {
        return querier.getLikeParameters();
    }

    public final Map<String, Object> getOrParameters() {
        return querier.getOrParameters();
    }

    public final Map<String, Object> getAndParameters() {
        return querier.getAndParameters();
    }

    public final void clearParameters() {
        querier.clearAll();
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
