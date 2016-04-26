package org.cheetah.fighter.plugin;

/**
 * Created by Max on 2016/3/7.
 */
public interface Plugin {

    Object intercept(Invocation invocation) throws Throwable;

    Object plugin(Object target);
}
