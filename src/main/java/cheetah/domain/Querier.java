package cheetah.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Max on 2016/1/6.
 */
public class Querier {
    private final SortList sortList = new SortList();
    private final Map<String, Object> and = new HashMap<String, Object>();
    private final Map<String, Object> or = new HashMap<String, Object>();
    private final Map<String, String> like = new HashMap<String, String>();
    private final List<String> groupby = new ArrayList<String>();

    public final SortList sortList() {
        return sortList;
    }

    public final void sort(List<String> properties, Sort.Order order) {
        sortList.add(properties, order);
    }

    public final void and(String name, Object value) {
        and.put(name, value);
    }

    public final void or(String name, Object value) {
        or.put(name, value);
    }

    public final  void like(String name, String value) {
        this.like.put(name, value);
    }

    public final  void groupby(String name) {
        this.groupby.add(name);
    }

    public final  List<String> groupby() {
        return this.groupby;
    }

    public final Map<String, String> getLikeParameters() {
        return like;
    }

    public final Map<String, Object> getOrParameters() {
        return or;
    }

    public final Map<String, Object> getAndParameters() {
        return and;
    }

    public final void clearAll() {
        this.sortList.clear();
        this.and.clear();
        this.or.clear();
        this.like.clear();
        this.groupby.clear();
    }

}
