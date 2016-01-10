package cheetah.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Max on 2016/1/5.
 */
public class OrderList {
    private List<Order> orders = new ArrayList<Order>();

    public void add(String property, Order.Direction direction) {
        orders.add(new Order(property, direction));
    }

    public void clear() {
        orders.clear();
    }

    public boolean isEmpty() {
        return orders.isEmpty();
    }

    public List<Order> orders() {
        return new ArrayList<Order>(orders);
    }

}


