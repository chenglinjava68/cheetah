package org.cheetah.rest.provider;

import org.cheetah.rest.ApiResult;

/**
 * Created by maxhuang on 2016/8/10.
 */
public class DefaultExceptionMessageConverter implements ExceptionMessageConverter<ApiResult> {
    @Override
    public ApiResult convert(Exception e) {
        return ApiResultHelper.doGetApiResult(e);
    }
}
