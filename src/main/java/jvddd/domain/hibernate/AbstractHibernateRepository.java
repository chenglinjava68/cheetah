package jvddd.domain.hibernate;

import jvddd.domain.AbstractEntity;
import jvddd.domain.Repository;
import jvddd.domain.TrackingId;

import java.util.List;

/**
 * Created by Max on 2015/12/31.
 */
public abstract class AbstractHibernateRepository<I extends TrackingId, T extends AbstractEntity<I>>
        implements Repository<I, T>{

    @Override
    public T get(I id) {
        return null;
    }

    @Override
    public void put(T object) {

    }

    @Override
    public void refresh(T object) {

    }

    @Override
    public void refresh(I objectId) {

    }

    @Override
    public boolean isRemoveAllowed() {
        return false;
    }

    @Override
    public void remove(T object) {

    }

    @Override
    public void remove(I objectId) {

    }

    @Override
    public List<T> list() {
        return null;
    }

    @Override
    public T getByPropertyValue(String propertyName, Object value) {
        return null;
    }

    @Override
    public void setFirst(int first) {

    }

    @Override
    public int getFirst() {
        return 0;
    }

    @Override
    public void setLast(int last) {

    }

    @Override
    public int getLast() {
        return 0;
    }

    @Override
    public void setOrder(String property, boolean reverse) {

    }

    @Override
    public void addOrder(String property, boolean reverse) {

    }

    @Override
    public void removeLastOrder() {

    }

    @Override
    public String getOrderProperty() {
        return null;
    }

    @Override
    public boolean isReverseOrder() {
        return false;
    }
}
