package org.cheetah.rest.provider;

import org.cheetah.rest.ApiResult;

/**
 * Created by maxhuang on 2016/8/10.
 */
public class DefaultSpringMvcExceptionMapper extends SpringMvcExceptionMapper<ApiResult> {

    public DefaultSpringMvcExceptionMapper() {
        super(new DefaultExceptionMessageConverter());
    }

}
