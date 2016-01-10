package cheetah.domain;

/**
 * Created by Max on 2016/1/10.
 */
public interface AmpleQuerier extends Querier {

    String groupby();

    void groupby(String name);

}
