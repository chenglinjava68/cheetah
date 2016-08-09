package org.cheetah.rest.provider;

import org.cheetah.rest.ApiResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Max on 2016/7/20.
 */
@ControllerAdvice
public class SpringMvcExceptionMapper {

    @ExceptionHandler
    @ResponseBody
    public ApiResult exceptionHandler(Exception e) {
        return ApiResultHelper.doGetApiResult(e);
    }
}
