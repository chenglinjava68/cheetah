package org.cheetah.rest.provider;

/**
 * Created by maxhuang on 2016/8/10.
 */
public interface ExceptionMessageConverter<T> {

    T convert(Exception e);

}
