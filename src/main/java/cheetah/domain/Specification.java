package cheetah.domain;

/**
 * Created by Max on 2016/1/7.
 */
public interface Specification<T extends Entity> {
    public enum Operator {
        AND,
        OR
    }

    void assembleCriteria(Assembly assembly);

}
