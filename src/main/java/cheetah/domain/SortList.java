package cheetah.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Max on 2016/1/5.
 */
public class SortList {
    private List<Sort> sorts = new ArrayList<Sort>();

    public void add(List<String> properties, Sort.Order order) {
        sorts.add(new Sort(properties, order));
    }

    public void clear() {
        sorts.clear();
    }


}


